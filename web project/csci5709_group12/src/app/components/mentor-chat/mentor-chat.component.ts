import { Component, OnInit } from '@angular/core';
import { UtilityService } from 'src/app/services/utilityservice.service';
import { Router } from '@angular/router';
import { mentorModel } from 'src/app/models/mentor.model';
import { HttpService } from 'src/app/services/httpservice.service';
import { MatDialog } from '@angular/material/dialog';
import { MessageComponent } from '../message/message.component';

@Component({
  selector: 'app-mentor-chat',
  templateUrl: './mentor-chat.component.html',
  styleUrls: ['./mentor-chat.component.css']
})
export class MentorChatComponent implements OnInit {

  constructor(private utilityService:  UtilityService, private router: Router,private httpservice: HttpService, private dialog: MatDialog, ) { }

  Mentors : mentorModel[];


  ngOnInit(): void {
    this.utilityService.sectionTitle = "Contact the Mentors"
    this.getMentors()
  }

  getMentors() {
    // fetch the list of mentors from the DB based on logged in user.
    this.httpservice.getServiceCall('/group-management/mentors')
    .subscribe( (result:any)=>{
      console.log(result)
      if(result.status){
      this.Mentors = result.data;
      }
      else{
        this.dialog.open(MessageComponent, {
          data: {
            type: 'E',
            title:'System Error',
            message: result.message,
          }
        });

      }
    },
    (error:any)=>{
      this.dialog.open(MessageComponent, {
        data: {
          type: 'E',
          title:'System Error',
          message: 'Something Went Wrong. Kindly Refresh the Page.',

        }
      });

    })

  }

}

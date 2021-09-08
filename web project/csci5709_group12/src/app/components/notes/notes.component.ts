// <!-- Mohammed Hamza Jasnak mh342039@dal.ca -->
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { DataService } from 'src/app/services/dataservice.service';
import { HttpService } from 'src/app/services/httpservice.service';
import { NotesService } from 'src/app/services/notesdata.service';
import { UtilityService } from 'src/app/services/utilityservice.service';
import { NoteModel } from '../../models/notes.model';
import { MessageComponent } from '../message/message.component';

@Component({
  selector: 'app-notes',
  templateUrl: './notes.component.html',
  styleUrls: ['./notes.component.css']
})
export class NotesComponent implements OnInit {

  Notes: NoteModel[];
  test: any = "test";

  constructor(private userdataservice: DataService, private dialog: MatDialog, private httpservice: HttpService,public dataservice: NotesService, private utilityService:  UtilityService, private router: Router) { }

  ngOnInit(): void {
    this.getNotes();
    this.utilityService.sectionTitle = "Notes"
  }

  openCard(index: any){
    if(index != -1){
    this.dataservice.setNote(this.Notes[index]);
    }
    else{
      this.dataservice.setNote(null);
    }
    this.router.navigate(["/main/notes-detail"])

  }


  
  getNotes() {
    // fetch the list of notes from the DB based on logged in user.
    this.httpservice.getServiceCall('/notes/'+this.userdataservice.loggedInUser.data._id)
    .subscribe( (result:any)=>{
      if(result.status){
      this.Notes = result.data;
      this.dataservice.setAllNote(this.Notes)

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

/*
 * Author: Misbah Pathan 
 * Email id: ms358232@dal.ca
 */

import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { DataService } from 'src/app/services/dataservice.service';
import { HttpService } from 'src/app/services/httpservice.service';
import { UtilityService } from 'src/app/services/utilityservice.service';
import { MessageComponent } from '../message/message.component';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  profile: any
  isEdit: boolean = false
  constructor(private dialog: MatDialog, private httpservice: HttpService, private userdataservice: DataService, private utilityservice: UtilityService) { }

  ngOnInit(): void {
    this.utilityservice.sectionTitle = "Profile"
    this.profile = this.userdataservice.loggedInUser.data
  }

  save(form: NgForm){
    if (form.invalid){
      return;
    }
    this.httpservice.putServiceCall("/updateprofile/" + this.userdataservice.loggedInUser.data._id,this.profile)
      .subscribe((result: any)=>{
        console.log(result)
        if (result.status) {
          this.dialog.open(MessageComponent, {
            data: {
              type: 'C',
              title: 'Success!',
              message: "Profile updated successfully",
              duration: 2000
            }
          });
        }
        else {
          this.dialog.open(MessageComponent, {
            data: {
              type: 'E',
              title: 'System Error',
              message: "Something went wrong. Please try again!",
            }
          });

        }

      },(error: any)=>{
        console.log(error)
        this.dialog.open(MessageComponent, {
          data: {
            type: 'E',
            title: 'System Error',
            message: "Something went wrong. Please try again!",
          }
        });

      })
  }

  delete(){

  }

}

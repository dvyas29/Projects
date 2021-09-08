/* 
 * Author: Mansi Singh 
 * Email id: mn518448@dal.ca
*/

import { ChangeDetectorRef, Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import {MatSnackBar} from '@angular/material/snack-bar';
// import { CdkTextareaAutosize } from '@angular/cdk/text-field';
import {take} from 'rxjs/operators';
import {AnnouncementService} from 'src/app/services/announcement.service';
import { Announcement } from 'src/app/models/announcements.model';
import { UtilityService } from 'src/app/services/utilityservice.service';
import { HttpService } from 'src/app/services/httpservice.service';
import { MatDialog } from '@angular/material/dialog';
import { MessageComponent } from '../message/message.component';
import { DataService } from 'src/app/services/dataservice.service';
import { templateJitUrl } from '@angular/compiler';

interface Category {
  value: string;
  viewValue: string;
}

@Component({
  selector: 'app-announcement-details',
  templateUrl: './announcement-details.component.html',
  styleUrls: ['./announcement-details.component.css']
})

export class AnnouncementDetailsComponent implements OnInit {
  announcement: Announcement= new Announcement();
  addAnnouncementForm: FormGroup;
  message: string;

  constructor(private userdataservice: DataService , private httpservice: HttpService, private dialog: MatDialog,private announcementService: AnnouncementService, public utilityService: UtilityService, private formBuilder: FormBuilder, private cdr: ChangeDetectorRef, private router: Router, private _snackBar: MatSnackBar) { 
    this.createForm();
  }

  ngOnInit(): void {
    this.getAnnouncement();
    this.cdr.detectChanges();
    this.utilityService.sectionTitle="Announcements";
  }

// this method fetches a specific announcement from the DB 
  getAnnouncement(){
    let temp= this.announcementService.getAnnouncement();
    if(temp){
      this.announcement=temp;
    }
    else
    this.announcement= new Announcement(); 
    console.log(this.userdataservice.loggedInUser.data._id)
    this.announcement.createdById = this.userdataservice.loggedInUser.data._id
    this.announcement.createdByName= this.userdataservice.loggedInUser.data.firstName + " " + this.userdataservice.loggedInUser.data.lastName
    this.announcement.createdOn = new Date();
  }

  categories: Category[] = [
    {value: 'cat-0', viewValue: 'Educational'},
    {value: 'cat-1', viewValue: 'Recreational'}
  ];


// this method is used to create a new form for announcement
  createForm(){
    this.addAnnouncementForm = this.formBuilder.group({
    Title: ['',[Validators.required,Validators.maxLength(30), this.utilityService.cannotContainSpace]],
    Category: ['', Validators.required],
    Description: ['', [Validators.required, this.utilityService.cannotContainSpace]],
   });
  }

// this method validates whether an announcement is to be added or updated when the "Save" button is clicked
onSaveAnnouncement(){
  if (this.announcement._id == -1) {
    this.postAnnouncement()
  }
  else {
    this.putAnnouncement()
  }
}

// this method creates a new announcement in the DB
// announcement details such as title, category, description, createdByName, createdById needs to be passed in the request body
postAnnouncement(){
  this.httpservice.postServiceCall('/announcements', this.announcement)
  .subscribe( (result:any)=>{
    console.log(result)
    if(result.status){
      this.dialog.open(MessageComponent, {
        data: {
          type: 'C',
          title:'Announcement Added',
          message: result.message,
          duration:2000
        }
      });    
      this.utilityService.isViewMyAnnouncementControlsVisible=false;
      this.router.navigate(['/main/announcement']);
    }else{
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
      let temp = {
        type: "E",
        title: 'System Error',
        message: "Something went wrong. Please try again!",
      }
      // if the error status is 409, a message is dsiplayed on the screen that the announcement already exists with the same title
      if(error.status == 409){
          temp = {
          type: "W",
          title: 'System Message',
          message: "Announcement with the same title already exists. Please update title!",
        }
      }
      this.dialog.open(MessageComponent, {
        data: temp
      });
    })
  }

// this method updates a specific announcement in the DB
// announcement id and announcement details which are to be upadted, needs to be passed in the request body
putAnnouncement(){
  this.httpservice.putServiceCall('/announcements/'+ this.announcement._id, this.announcement)
  .subscribe( (result:any)=>{
    console.log(result)
    if(result.status){
      this.dialog.open(MessageComponent, {
        data: {
          type: 'C',
          title:'Announcement Updated',
          message: result.message,
          duration:2000
        }
      });    
      this.utilityService.isViewMyAnnouncementControlsVisible=false;
      this.router.navigate(['/main/announcement']);
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

// this method deletes a specific announcement from the DB
// announcement id is passed in the request body
onDeleteAnnouncement(){
  let index= this.announcementService.getAnnouncementIndex(this.announcement._id);
  if(index > -1){
  this.announcementService.deleteAnnouncement(this.announcement._id);
  }
  }

}



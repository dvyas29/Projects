/* 
 * Author: Mansi Singh 
 * Email id: mn518448@dal.ca
*/

import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { MessageComponent } from '../components/message/message.component';
import {Announcement} from '../models/announcements.model';
import { DataService } from './dataservice.service';
import { HttpService } from './httpservice.service';
import { UtilityService } from './utilityservice.service';

@Injectable({
  providedIn: 'root'
})

export class AnnouncementService {
  announcement:any;
  allAnnouncements: Announcement[] =[];

constructor(public userdataservice: DataService, public utilityservice: UtilityService, private httpservice: HttpService, private dialog: MatDialog, private router: Router) { 
}

getAnnouncement(){
  return this.announcement ;
}

setAnnouncement(announcement:any){
this.announcement=announcement;
}

setAllAnnouncements(announcements:Announcement[]){
  this.allAnnouncements = announcements;
}

// this method is used to delete a specific announcement from the DB
// announcement id is passed in the url parameter
deleteAnnouncement(id:any){
  const dialogRef= this.dialog.open(MessageComponent, {
    data: {
      type: 'W',
      title:'Are you sure?',
      message: 'You will not be able to recover this announcement!',
      buttonText:'Yes, delete it!',
    }
  });

dialogRef.afterClosed().subscribe(result => {
  let index: number;
  if(result.data == "Y"){
    this.httpservice.deleteServiceCall('/announcements/' + id, {})
        .subscribe( (result:any)=>{
          console.log(result)
          if(result.status){
            for (let i = 0; i < this.allAnnouncements.length; i++) {
              if(this.allAnnouncements[i]._id == id){
                 index = i;
                }
               }
            // let index = this.allAnnouncements.findIndex(o=>{ return o._id = id})
              this.allAnnouncements.splice(index,1);
              this.router.navigate(['/main/announcement']);
              this.dialog.open(MessageComponent, {
                data: {
                  type: 'C',
                  title:'Deleted',
                  message: 'Announcement deleted successfully.',
                  duration:2000
                }
              });
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
  });
}

// this method is used to fetch a specific announcement from the DB based on the announcement selected from the UI
getAnnouncementIndex(id:number){
  return this.allAnnouncements.map(o => o._id).indexOf(id);
}

// this method is used to fetch a specific announcement from the DB
getAnnouncements(){
  this.httpservice.getServiceCall('/announcements')
  .subscribe( (result:any)=>{
    if(result.status){
      this.allAnnouncements = result.data;

      // fetch the announcements created by the logged in user from the DB on clicking "View my announcements"
      if(this.utilityservice && this.utilityservice.isViewMyAnnouncementControlsVisible){
        this.allAnnouncements = this.allAnnouncements.filter((announcement: any) => {
          return announcement.createdById == this.userdataservice.loggedInUser.data._id;
      });
      }
    // in case no announcements are there, a warning message will be displayed on the screen
      if(this.allAnnouncements.length == 0){
        this.dialog.open(MessageComponent, {
          data: {
            type: 'W',
            title:'Warning',
            message: "There are no announcements. Please create announcements in order to view them here!",
          }
        });
      }
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

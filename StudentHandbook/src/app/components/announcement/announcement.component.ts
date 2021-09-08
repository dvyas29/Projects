/* 
 * Author: Mansi Singh 
 * Email id: mn518448@dal.ca
*/

import { Component, OnInit } from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Announcement } from 'src/app/models/announcements.model';
import {AnnouncementService} from 'src/app/services/announcement.service';
import { DataService } from 'src/app/services/dataservice.service';
import { HttpService } from 'src/app/services/httpservice.service';
import { UtilityService } from 'src/app/services/utilityservice.service';
import { MessageComponent } from '../message/message.component';

@Component({
  selector: 'app-announcement',
  templateUrl: './announcement.component.html',
  styleUrls: ['./announcement.component.css']
})
export class AnnouncementComponent implements OnInit {

  Announcements:Announcement[];
  showMore:any;
  isReadMore: boolean= true;
  user: any;

  constructor(public userdataservice: DataService, private dialog: MatDialog, private httpservice: HttpService,private router: Router, public utilityService: UtilityService ,public announcementService: AnnouncementService) { }

  ngOnInit(): void {
    // this menthod fetches the list of announcements from the DB based on logged in user
    this.announcementService.getAnnouncements();
    if(!this.announcementService.allAnnouncements){
      this.dialog.open(MessageComponent, {
        data: {
          type: 'E',
          title:'System Error',
          message: "There are no announcements!",
        }
      });
    }

    this.user=this.userdataservice.loggedInUser.data._id
    
    this.utilityService.sectionTitle="Announcements";
  }

  // this method opens a specific announcement when Edit button is clicked from the Menu
  // announcement id is being passed as an input
  onOpenAnnouncement(index:any){
    if(index != -1){
      this.announcementService.setAnnouncement(this.announcementService.allAnnouncements[index]);
      }
      else{
        this.announcementService.setAnnouncement(null);
      }
      this.router.navigate(["/main/announcementdetails"])
  } 

}


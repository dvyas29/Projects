/* 
 * Author: Mansi Singh 
 * Email id: mn518448@dal.ca
*/

import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { AppFeedbackModel } from 'src/app/models/appFeedback.model';
import { HttpService } from 'src/app/services/httpservice.service';
import { UtilityService } from 'src/app/services/utilityservice.service';
import { MessageComponent } from '../message/message.component';

interface UsageFrequecny {
  value: string;
  viewValue: string;
}

interface Rating {
  value: string;
  viewValue: string;
}

interface LikedFeature{
  value: string;
  viewValue: string;
}

@Component({
  selector: 'app-rate-application',
  templateUrl: './rate-application.component.html',
  styleUrls: ['./rate-application.component.css']
})
export class RateApplicationComponent implements OnInit {
  applicationFeedbackForm: AppFeedbackModel = new AppFeedbackModel();
  websiteFeedbackForm: FormGroup;
  message: string;

  constructor(private utilityService: UtilityService, private httpservice: HttpService,private dialog: MatDialog, private formBuilder: FormBuilder, private router: Router,private route: ActivatedRoute, private _snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.utilityService.sectionTitle="Application Feedback";
    this.createAppFeedbackForm();
  }

  usageFreqs: UsageFrequecny[] = [
    {value: 'v1', viewValue: 'I use it everyday'},
    {value: 'v2', viewValue: 'I use it sometimes'},
    {value: 'v3', viewValue: 'I do not use it at all'},
  ];

  ratings: Rating[] = [
    {value: 'v1', viewValue: '1'},
    {value: 'v2', viewValue: '2'},
    {value: 'v2', viewValue: '3'},
    {value: 'v2', viewValue: '4'},
    {value: 'v2', viewValue: '5'}
  ];

  likedFeatures: LikedFeature[] = [
    {value: 'v1', viewValue: 'Application UI'},
    {value: 'v2', viewValue: 'Application Functionality: Notes'},
    {value: 'v3', viewValue: 'Application Functionality: Scheduler'},
    {value: 'v4', viewValue: 'Application Functionality: Announcements'},
    {value: 'v5', viewValue: 'Application Functionality: Peer Mentorship Program'},
    {value: 'v6', viewValue: 'Application Functionality: Student Wall'}
  ];

  // this method is used for creating a form for application feedback
  createAppFeedbackForm(){
    this.websiteFeedbackForm = this.formBuilder.group({
    UsageFrequency: ['',Validators.required],
    Feedback: ['', [Validators.required, this.utilityService.cannotContainSpace]],
    Rating: ['', Validators.required],
    LikedFeature: ['', Validators.required]
   });
  }

  // this method is used for submitting application feedback
  // feedback details such as application usage frequency, most liked features, rating, feedback description, and timestamp needs to be passed as a request body
  onSubmit() {
    if(this.websiteFeedbackForm.valid){
    this.httpservice.postServiceCall('/feedback/application', this.applicationFeedbackForm)
    .subscribe((result:any)=>{
      if(result.status){
        this.disableForm();
        this.dialog.open(MessageComponent, {
          data: {
            type: 'C',
            title:'Application Feedback Submitted Successful',
            message: 'Your feedback has been submitted. Thank you for your time!',
            duration:3000
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
          message: 'Something Went Wrong. Please Try Again.',
        }
      });
    });
  }
  }

  //this method is used for disabling feedback form after the feedback has been submitted
  disableForm(){
    this.websiteFeedbackForm.get('UsageFrequency').disable();
    this.websiteFeedbackForm.get('LikedFeature').disable();
    this.websiteFeedbackForm.get('Rating').disable();
    this.websiteFeedbackForm.get('Feedback').disable();
  }

}


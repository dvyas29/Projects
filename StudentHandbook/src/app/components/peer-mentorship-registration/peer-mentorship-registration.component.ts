/**
* Author: Gurleen Saluja (gr997570@dal.ca)
*/
import { Component, OnInit, Inject } from '@angular/core';
import { UtilityService } from '../../services/utilityservice.service';
import { FormControl, FormBuilder, Validators, FormGroup } from '@angular/forms';
import { MomentDateAdapter, MAT_MOMENT_DATE_ADAPTER_OPTIONS } from '@angular/material-moment-adapter';
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';
import { MatDatepicker } from '@angular/material/datepicker';
import { PeerMentorshipRegistrationModel } from 'src/app/models/peer-mentorship-registration.model';
import { HttpService } from 'src/app/services/httpservice.service';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { MessageComponent } from '../message/message.component';
import { DataService } from '../../services/dataservice.service';

/* Moment code adapted from angular.io. */
import * as _moment from 'moment';
import {default as _rollupMoment, Moment} from 'moment';

const moment = _rollupMoment || _moment;
export const MY_FORMATS = {
  parse: {
    dateInput: 'MM/YYYY',
  },
  display: {
    dateInput: 'MM/YYYY',
    monthYearLabel: 'MMM YYYY',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'MMMM YYYY',
  },
};

@Component({
  selector: 'app-peer-mentorship-registration',
  templateUrl: './peer-mentorship-registration.component.html',
  styleUrls: ['./peer-mentorship-registration.component.css'],
  providers: [
    {
      provide: DateAdapter,
      useClass: MomentDateAdapter,
      deps: [MAT_DATE_LOCALE, MAT_MOMENT_DATE_ADAPTER_OPTIONS]
    },

    {provide: MAT_DATE_FORMATS, useValue: MY_FORMATS},
  ],
})
export class PeerMentorshipRegistrationComponent implements OnInit {
  registerUser: PeerMentorshipRegistrationModel = new PeerMentorshipRegistrationModel();
  registrationForm: any;
  preferences = ['Male', 'Female', 'No Preference'];
  selectedRole="mentee";
  locations = ['Canada', 'India', 'US', 'Brazil', 'China'];
  campusLocations = ['Halifax', 'Truro'];
  minDate = new Date(new Date().getFullYear() - 4, 0, 1);
  maxDate = new Date(new Date().getFullYear() + 4, 0, 1);
  map: Map<string, string[]>;
  faculties: string[] = [];
  programs: string[] = [];
  freshRegistration = false;
  formattedStartDate: any;
  formattedEndDate: any;
  success: boolean = false;
  failure: boolean = false;
  isRegistrationOpen: boolean;
  currentDate = new Date();
  registrationStartDate: any;
  reopenRegistration = false;
  memberAlreadyExists = false;

  constructor(public util: UtilityService, private fb: FormBuilder, private httpservice: HttpService, private dialog: MatDialog,
    private router: Router, public dataservice: DataService) {
    this.generateProgramMap();
    this.registrationForm = this.fb.group({
      role:[''],
      email:['', {validators: [Validators.pattern('[a-z0-9._%+-]+@(dal)+\\.(ca)')], updateOn: 'blur'}],
      name:['', [Validators.pattern('^[A-Za-z0-9 ]+$')]],
      faculty:[''],
      program:[''],
      startDate:[''],
      endDate:[''],
      location:[''],
      preference:[''],
      campusLocation:['']
    },
    {validators: [validateProgram("faculty", "program", this.map),
      validateDates("startDate", "endDate", "role")]});
    if(!dataservice.isAdmin){
      this.registerUser.email = this.dataservice.loggedInUser.data.email;
      this.registrationForm.get('email').disable();
      this.registerUser.name = this.dataservice.loggedInUser.data.firstName+" "+this.dataservice.loggedInUser.data.lastName;
      this.registrationForm.get('name').disable();
      this.checkIfRegistrationOpen();
    }
    else{
      this.disableForm();
      this.isRegistrationOpen = true;
    }
  }

  /* Checks if peer mentorship program registrations are open. */
  checkIfRegistrationOpen(){
    this.httpservice.getServiceCall('/manage-registration')
    .subscribe((result:any)=>{
      if(result.status && result.data){
        if(new Date(result.data.startDate) <= this.currentDate && new Date(result.data.endDate) >= this.currentDate)
          this.isRegistrationOpen = true;
        else
          this.isRegistrationOpen = false;
        this.registrationStartDate = new Date(result.data.startDate);
      }
      else{
        this.isRegistrationOpen = false;
      }
    });
    this.checkRegistration();
  }

  /*
    Sets section title.
    For a new user sets default role and preference.
  */
  ngOnInit(): void {
    this.util.sectionTitle = "Peer Mentorship Registration";
    if(!this.registerUser.isRegistered){
      this.registerUser.role = "Mentee";
      this.registerUser.preference = this.preferences[2];
      this.freshRegistration = true;
    }
  }

  /* Generates a map of program and faculty. */
  generateProgramMap(){
    this.map = new Map([
      ['Faculty of Computer Science', ['Bachelor of Computer Science', 'Master of Applied Computer Science']],
      ['Faculty of Management', ['Bachelor of Commerce', 'Master of Business Administration']]
    ]);
    for(let key of this.map.keys()){
      this.faculties.push(key);
    }
    for(let value of this.map.values()){
      for(let innerValue of value){
          this.programs.push(innerValue);
      }
    }
  }

  /* Binds the year selected in start date with model. */
  chosenStartYearHandler(normalizedYear: Moment) {
    if(this.registrationForm.value.startDate != undefined){
      const ctrlValue = this.registrationForm.value.startDate;
      ctrlValue.year(normalizedYear.year());
      this.registrationForm.controls['startDate'].setValue(ctrlValue);
    }
    else{
      const ctrlValue = moment(this.minDate);
      ctrlValue.year(normalizedYear.year());
      this.registrationForm.controls['startDate'].setValue(ctrlValue);
    }
  }

  /* Binds the month selected in start date with model. */
  chosenStartMonthHandler(normalizedMonth: Moment, datepicker: MatDatepicker<Moment>) {
    if(this.registrationForm.value.startDate != undefined){
      const ctrlValue = this.registrationForm.value.startDate;
      ctrlValue.month(normalizedMonth.month());
      this.registrationForm.controls['startDate'].setValue(ctrlValue);
    }
    else{
      const ctrlValue = moment(this.minDate);
      ctrlValue.year(normalizedMonth.year());
      this.registrationForm.controls['startDate'].setValue(ctrlValue);
    }
    datepicker.close();
  }

  /* Binds the year selected in end date with model. */
  chosenEndYearHandler(normalizedYear: Moment) {
    if(this.registrationForm.value.endDate != undefined){
      const ctrlValue = this.registrationForm.value.endDate;
      ctrlValue.year(normalizedYear.year());
      this.registrationForm.controls['endDate'].setValue(ctrlValue);
    }
    else{
      const ctrlValue = moment(this.maxDate);
      ctrlValue.year(normalizedYear.year());
      this.registrationForm.controls['endDate'].setValue(ctrlValue);
    }
  }

  /* Binds the month selected in start date with model. */
  chosenEndMonthHandler(normalizedMonth: Moment, datepicker: MatDatepicker<Moment>) {
    if(this.registrationForm.value.endDate != undefined){
      const ctrlValue = this.registrationForm.value.endDate;
      ctrlValue.month(normalizedMonth.month());
      this.registrationForm.controls['endDate'].setValue(ctrlValue);
    }
    else{
      const ctrlValue = moment(this.maxDate);
      ctrlValue.year(normalizedMonth.year());
      this.registrationForm.controls['endDate'].setValue(ctrlValue);
    }
    datepicker.close();
  }

  /* Saves or updates user data and opens dialog for confirmation or failure. */
  openDialog() {
    this.setStaticData();
    if(this.registerUser._id == -1){
      this.saveUser();
    }
    else{
      this.updateUser();
    }
    this.registerUser.startDate = this.formattedStartDate;
    this.registerUser.endDate = this.formattedEndDate;
  }

  /* Sets model for PUT call. */
  setStaticData(){
    this.registerUser.requestType = "Registration";
    this.registerUser.requestDate = this.currentDate;
    this.registerUser.requestStatus = "Pending";
    this.registerUser.modificationDate = this.currentDate;
    this.formattedStartDate = this.registerUser.startDate;
    this.registerUser.startDate = this.registerUser.startDate._i;
    this.formattedEndDate = this.registerUser.endDate;
    this.registerUser.endDate = this.registerUser.endDate._i;
  }

  /* Makes POST request call to the server. */
  saveUser(){
    this.httpservice.postServiceCall('/peer-mentorship-registration', this.registerUser)
    .subscribe((result:any)=>{
      if(result.status){
        this.disableForm();
        this.dialog.open(MessageComponent, {
          data: {
            type: 'C',
            title:'Registration Successful',
            message: 'Registration request raised. Please check back for approval in some time.',
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
            duration:3000
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
          duration:3000
        }
      });
    });
  }

  /* Disbales all form fields. */
  disableForm(){
    this.registrationForm.get('role').disable();
    this.registrationForm.get('faculty').disable();
    this.registrationForm.get('program').disable();
    this.registrationForm.get('startDate').disable();
    this.registrationForm.get('endDate').disable();
    this.registrationForm.get('location').disable();
    this.registrationForm.get('preference').disable();
    this.registrationForm.get('campusLocation').disable();
  }

  /* Enables all form fields. */
  enableForm(){
    this.registrationForm.get('role').enable();
    this.registrationForm.get('faculty').enable();
    this.registrationForm.get('program').enable();
    this.registrationForm.get('startDate').enable();
    this.registrationForm.get('endDate').enable();
    this.registrationForm.get('location').enable();
    this.registrationForm.get('preference').enable();
    this.registrationForm.get('campusLocation').enable();
  }

  /* Updates user details. */
  updateUser(){
    this.registerUser.requestStatus = 'Pending';
    this.registerUser.requestDate = moment(this.currentDate);
    this.httpservice.putServiceCall('/peer-mentorship-registration/'+this.registerUser._id, this.registerUser).subscribe((result:any)=>{
      if(result.status){
        this.disableForm();
        this.dialog.open(MessageComponent, {
          data: {
            type: 'C',
            title:'Registration Successful',
            message: 'Registration request raised. Please check back for approval in some time.',
            duration:3000
          }
        });
      }
    });
  }

  /* Checks if a user has registered with the Peer Mentorship Program. */
  checkRegistration(){
    if(this.registerUser.email !== undefined){
      this.httpservice.getServiceCall('/peer-mentorship-registration/'+this.registerUser.email)
      .subscribe((result:any)=>{
        if(result.status && result.data){
          this.setResponse(result);
          if(!this.reopenRegistration)
            this.disableForm();
        }
      },
      (error:any)=>{
        this.dialog.open(MessageComponent, {
          data: {
            type: 'E',
            title:'System Error',
            message: 'Something Went Wrong. Kindly Refresh the Page.',
            duration:3000
          }
        });
      });
    }
  }

  /* Sets response objects to model. */
  setResponse(result: any){
    this.registerUser._id = result.data._id;
    this.registerUser.email = result.data.email;
    this.registerUser.name = result.data.name;
    this.registerUser.faculty = result.data.faculty;
    this.registerUser.program = result.data.program;
    this.registerUser.startDate = moment(result.data.startDate, "MM/YYYY");
    this.registerUser.endDate = moment(result.data.endDate, "MM/YYYY");
    this.registerUser.location = result.data.location;
    this.registerUser.campusLocation = result.data.campusLocation;
    this.registerUser.preference = result.data.preference;
    this.registerUser.role = result.data.role;
    this.registerUser.isRegistered = result.data.isRegistered;
    this.registerUser.requestType = result.data.requestType;
    this.registerUser.requestStatus = result.data.requestStatus;
    this.registerUser.requestDate = moment(result.data.requestDate);
    if(this.registerUser.isRegistered !== undefined && this.registerUser.isRegistered)
      this.success = true;
    else if(this.registerUser.requestType === 'Registration' && this.registerUser.requestStatus === 'Declined' && moment(this.registrationStartDate).isAfter(this.registerUser.requestDate)){
      this.reopenRegistration = true;
      this.enableForm();
    }
    else if(this.registerUser.isRegistered !== undefined && !this.registerUser.isRegistered && this.registerUser.requestStatus === 'Declined'){
      this.failure = true;
    }
  }

  /* Checks if user is registered with the application. */
  checkIfUserExists(){
    if(this.dataservice.isAdmin){
      if(this.registrationForm.controls["email"].value !== undefined && this.registrationForm.controls["email"].value !== ""){
        this.httpservice.getServiceCall('/registration/'+this.registrationForm.controls["email"].value)
        .subscribe((result:any)=>{
          if(result.status && result.data){
            this.registrationForm.controls["email"].setErrors(null);
            this.registrationForm.controls["name"].setValue(result.data.firstName+" "+result.data.lastName);
            this.registrationForm.controls["email"].disable();
            this.registrationForm.controls["name"].disable();
            this.enableForm();
            this.checkRegistration();
            if(this.registerUser.isRegistered){
              this.memberAlreadyExists = true;
            }
          }
          else{
            this.registrationForm.controls["email"].setErrors({notRegistered: true});
          }
        });
      }
    }
  }
}

/* Validates and matches faculties and programs. */
function validateProgram(faculty: any, program: any, map: Map<string, string[]>){
  return (formGroup: FormGroup) => {
    if(map != undefined && formGroup.controls[faculty].value != undefined && map.has(formGroup.controls[faculty].value)){
      const programs = map.get(formGroup.controls[faculty].value);
      const programFound = programs.find(elem => elem === formGroup.controls[program].value);
      if(!programFound){
        formGroup.controls[program].setErrors({mismatch: true});
      }
      else{
        formGroup.controls[program].setErrors(null);
      }
    }
  }
}

/* Validates start and end dates.
    1. Checks if start date and end date is equal.
    2. Checks if end date is less than current date.
    3. Checks if selected role is Mentor and less than 8 months have
       passed then disallows user to register as a Mentor.
*/
function validateDates(startDate: any, endDate: any, role: string) {
  return (formGroup: FormGroup) => {
    if(formGroup.controls[startDate].value != undefined && formGroup.controls[startDate].value._d != undefined
      && formGroup.controls[endDate].value != undefined && formGroup.controls[endDate].value._d != undefined){
      if(formGroup.controls[startDate].value._d.getFullYear() == formGroup.controls[endDate].value._d.getFullYear() && formGroup.controls[startDate].value._d.getMonth() == formGroup.controls[endDate].value._d.getMonth()){
        formGroup.controls[startDate].setErrors({match: true});
        formGroup.controls[endDate].setErrors({match: true});
        return;
      }
      else{
        formGroup.controls[startDate].setErrors(null);
        formGroup.controls[endDate].setErrors(null);
      }
      if(formGroup.controls[endDate].value._d.getFullYear() <= new Date().getFullYear() && formGroup.controls[endDate].value._d.getMonth() <= new Date().getMonth()){
        formGroup.controls[endDate].setErrors({invalid: true});
        return;
      }
      else{
        formGroup.controls[endDate].setErrors(null);
      }
      if(moment(moment.now()).diff(moment(formGroup.controls[startDate].value._d), 'months') <= 8
        && formGroup.controls[role].value === 'Mentor'){
        formGroup.controls[startDate].setErrors({invalidDiff: true});
        return;
      }
      else{
        formGroup.controls[endDate].setErrors(null);
      }
    }
  }
}

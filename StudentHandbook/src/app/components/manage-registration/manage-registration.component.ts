/**
* Author: Gurleen Saluja (gr997570@dal.ca)
*/
import { Component, OnInit } from '@angular/core';
import { FormControl, FormBuilder, Validators, FormGroup } from '@angular/forms';
import { MomentDateAdapter, MAT_MOMENT_DATE_ADAPTER_OPTIONS } from '@angular/material-moment-adapter';
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';
import { MatDatepicker } from '@angular/material/datepicker';
import { UtilityService } from '../../services/utilityservice.service';
import { HttpService } from 'src/app/services/httpservice.service';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { MessageComponent } from '../message/message.component';
import { ManageRegistrationModel } from 'src/app/models/manage-registration.model';

@Component({
  selector: 'app-manage-registration',
  templateUrl: './manage-registration.component.html',
  styleUrls: ['./manage-registration.component.css']
})
export class ManageRegistrationComponent implements OnInit {

  enableRegistrationForm: any;

  manageRegistration = new ManageRegistrationModel();

  hideWindowBtn = true;

  constructor(public util: UtilityService, private fb: FormBuilder,
    private httpservice: HttpService, private dialog: MatDialog) {
    this.enableRegistrationForm = this.fb.group({
      startDate: ["", [Validators.required]],
      endDate: ["", [Validators.required]]
    },
    {
      validator: validateDates("startDate", "endDate")
    });
  }

  /* Sets section title. */
  ngOnInit(): void {
    this.util.sectionTitle = "Manage Registration";
    this.setForm(false);
  }

  /*
    Fetches data from database and if the date in database is less than current date
    then, resets the form otherwise, disables the form and enables a close window button.
  */
  setForm(reset: boolean){
    this.httpservice.getServiceCall('/manage-registration')
    .subscribe((result:any)=>{
      if(result.status && result.data){
        this.manageRegistration._id = result.data._id;
        if(new Date(result.data.endDate) < new Date())
          this.resetForm();
        else{
          this.manageRegistration._id = result.data._id;
          this.manageRegistration.startDate = new Date(result.data.startDate);
          this.manageRegistration.endDate = new Date(result.data.endDate);
          this.enableRegistrationForm.get('startDate').disable();
          this.enableRegistrationForm.get('endDate').disable();
          this.hideWindowBtn = false;
        }
        if(reset)
          this.resetForm();
      }
    });
  }

  /* Writes the data into the database. */
  submitForm(){
    this.httpservice.postServiceCall('/manage-registration', this.manageRegistration)
    .subscribe((result:any)=>{
      if(result.status){
        this.enableRegistrationForm.get('startDate').disable();
        this.enableRegistrationForm.get('endDate').disable();
        this.hideWindowBtn = false;
        this.dialog.open(MessageComponent, {
          data: {
            type: 'C',
            title:'Dates Logged Successfully',
            message: 'Registration will be opened on '+new Date(this.manageRegistration.startDate)+' and will end on '+new Date(this.manageRegistration.endDate),
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

  /* Deletes the data from the database. */
  resetForm(){
    this.httpservice.deleteServiceCall('/manage-registration/'+this.manageRegistration._id, this.manageRegistration).subscribe((result:any)=>{
      if(result.status){
        console.log("Dates deleted!Window closed");
        this.hideWindowBtn = true;
        this.enableRegistrationForm.get('startDate').enable();
        this.enableRegistrationForm.get('endDate').enable();
        this.enableRegistrationForm.reset();
      }
    });
  }
}

/* Validates start and end dates.
    1. Checks if start date and end date is equal.
    2. Checks if end date is less than current date.
*/
function validateDates(startDate: any, endDate: any){
  var currentDate = new Date();
  return (formGroup: FormGroup) => {
    if(formGroup.controls[startDate].value != undefined && formGroup.controls[startDate].value._d != undefined){
        if(formGroup.controls[startDate].value._d.getFullYear() < currentDate.getFullYear()){
          formGroup.controls[startDate].setErrors({invalid: true});
          return;
        }
        else if(formGroup.controls[startDate].value._d.getFullYear() == currentDate.getFullYear()
          && formGroup.controls[startDate].value._d.getMonth() < currentDate.getMonth()){
          formGroup.controls[startDate].setErrors({invalid: true});
          return;
        }
        else if(formGroup.controls[startDate].value._d.getFullYear() == currentDate.getFullYear()
          && formGroup.controls[startDate].value._d.getMonth() == currentDate.getMonth()
          && formGroup.controls[startDate].value._d.getDate() < currentDate.getDate()){
            formGroup.controls[startDate].setErrors({invalid: true});
            return;
        }
        else{
          formGroup.controls[startDate].setErrors(null);
        }
      }
      if(formGroup.controls[endDate].value != undefined && formGroup.controls[endDate].value._d != undefined){
        if(formGroup.controls[endDate].value._d.getFullYear() < currentDate.getFullYear()){
          formGroup.controls[endDate].setErrors({invalid: true});
          return;
        }
        else if(formGroup.controls[endDate].value._d.getFullYear() == currentDate.getFullYear()
          && formGroup.controls[endDate].value._d.getMonth() < currentDate.getMonth()){
          formGroup.controls[endDate].setErrors({invalid: true});
          return;
        }
        else if(formGroup.controls[endDate].value._d.getFullYear() == currentDate.getFullYear()
          && formGroup.controls[endDate].value._d.getMonth() == currentDate.getMonth()
          && formGroup.controls[endDate].value._d.getDate() < currentDate.getDate()){
            formGroup.controls[endDate].setErrors({invalid: true});
            return;
        }
        else{
          formGroup.controls[endDate].setErrors(null);
        }
      }
      if(formGroup.controls[startDate].value != undefined && formGroup.controls[startDate].value._d != undefined
        && formGroup.controls[endDate].value != undefined && formGroup.controls[endDate].value._d != undefined) {
        if(Math.floor((Date.UTC(formGroup.controls[endDate].value._d.getFullYear(), formGroup.controls[endDate].value._d.getMonth(), formGroup.controls[endDate].value._d.getDate())
        - Date.UTC(formGroup.controls[startDate].value._d.getFullYear(), formGroup.controls[startDate].value._d.getMonth(), formGroup.controls[startDate].value._d.getDate()) ) /(1000 * 60 * 60 * 24)) < 1){
          formGroup.controls[endDate].setErrors({minDiff: true});
          return;
        }
        else{
          formGroup.controls[endDate].setErrors(null);
        }
      }
    }
}

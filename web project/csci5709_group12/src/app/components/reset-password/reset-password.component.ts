/**
 * Author: Misbah Pathan 
 * Email id: ms358232@dal.ca
 */
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatStepper } from '@angular/material/stepper';
import { Router } from '@angular/router';
import { DataService } from 'src/app/services/dataservice.service';
import { HttpService } from 'src/app/services/httpservice.service';
import { MessageComponent } from '../message/message.component';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {
  isLinear = true;
  ProfileFormGroup!: FormGroup;
  SecurityQFormGroup!: FormGroup;
  EmailFormGroup!: FormGroup;

  constructor(private httpservice: HttpService, private _formBuilder: FormBuilder, private dialog: MatDialog, private router: Router, private dataservice: DataService) { }

  ngOnInit() {

    this.EmailFormGroup = this._formBuilder.group({
      Email: [{ value: this.dataservice.loggedInUser.data.email, disabled: true }, [Validators.required, Validators.pattern("([a-zA-Z0-9-_.]+[@]+[a-zA-Z0-9-_.]+[.]+[a-zA-Z0-9]+[a-zA-Z0-9]+)")]],
      Password: ['', [Validators.required, Validators.minLength(8)]],
    });


    this.ProfileFormGroup = this._formBuilder.group({
      Password: ['', [Validators.required, Validators.minLength(8)]],
      ConfirmPassword: ['', Validators.required]
    }, {
      validator: this.checkPassword()
    });

    this.SecurityQFormGroup = this._formBuilder.group({
      firstCtrl: ['', Validators.required],
      secondCtrl: ['', Validators.required]
    });

  }

  checkPassword() {
    return (group: FormGroup) => {
      const password = group.controls['Password'];
      const confirmPassword = group.controls['ConfirmPassword'];
      if (confirmPassword.errors && confirmPassword.errors.mismatch) {
        return;
      }
      if (password.value !== confirmPassword.value) {
        confirmPassword.setErrors({ mismatch: true });
      }
    }
  }

  validateCreds(stepper: MatStepper) {
    console.log("validate cred")
    var req = {
      email: this.EmailFormGroup.controls["Email"].value,
      password: this.EmailFormGroup.controls["Password"].value
    }
    this.httpservice.postServiceCall("/registration/login", req)
      .subscribe((result: any) => {
        console.log(result)
        if (result.status) {
          stepper.next();
        }
        else {
          this.dialog.open(MessageComponent, {
            data: {
              type: 'W',
              title: 'System Mesaage',
              message: "Incorrect Credentials. Please try again!",
            }
          });

        }

      }, (error: any) => {
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

  validateSecretQuestion(stepper: MatStepper) {
    let req = {
      firstQuestion: this.SecurityQFormGroup.controls["firstCtrl"].value,
      secondQuestion: this.SecurityQFormGroup.controls["secondCtrl"].value
    }
    this.httpservice.postServiceCall("/updateprofile/checkquestion/" + this.dataservice.loggedInUser.data._id, req)
      .subscribe((result: any) => {
        console.log(result)
        if (result.status) {
          stepper.next();
        }
        else {
          this.dialog.open(MessageComponent, {
            data: {
              type: 'W',
              title: 'System Message',
              message: "Incorrect answers. Please try again!",
            }
          });

        }

      }, (error: any) => {
        console.log(error)
        let temp = {
          type: "E",
          title: 'System Error',
          message: "Incorrect answers. Please try again!",
        }
        if(error.status == 401){
          temp = {
            type: "W",
            title: 'System Message',
            message: "Something went wrong. Please try again!",
          }
        }
        this.dialog.open(MessageComponent, {
        data: temp
      });

      })
  }

  save() {
    let req = {
      password: this.ProfileFormGroup.controls["Password"].value,
    }
    this.httpservice.putServiceCall("/updateprofile/resetpassword/" + this.dataservice.loggedInUser.data._id, req)
      .subscribe((result: any) => {
        if (result.status) {
          this.router.navigate(['/main/profile']);
          this.dialog.open(MessageComponent, {
            data: {
              type: 'C',
              title: 'Success!',
              message: "Password Reset Successful",
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
      }, (error: any) => {
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
}

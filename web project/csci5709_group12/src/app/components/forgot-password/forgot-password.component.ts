import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { HttpService } from 'src/app/services/httpservice.service';
import { MessageComponent } from '../message/message.component';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {
  isLinear = true;
  ProfileFormGroup!: FormGroup;
  SecurityQFormGroup!: FormGroup;
  EmailFormGroup!: FormGroup;

  Email: "";
  Password: "";
  ConfirmPassword: "";

  firstCtrl: "";
  secondCtrl: "";
  
  constructor(private dialog: MatDialog ,private _formBuilder: FormBuilder, private router: Router, private httpservice: HttpService) {}

  ngOnInit() {

    this.EmailFormGroup = this._formBuilder.group({
      Email: ['', [Validators.required, Validators.pattern("([a-zA-Z0-9-_.]+[@]+[a-zA-Z0-9-_.]+[.]+[a-zA-Z0-9]+[a-zA-Z0-9]+)")]],
    });

    this.ProfileFormGroup = this._formBuilder.group({
      Password: ['', [Validators.required, Validators.minLength(8)]],
      ConfirmPassword: ['', Validators.required]
   },{
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
      console.log(confirmPassword.value)
      if (password.value !== confirmPassword.value) {
        confirmPassword.setErrors({ mismatch: true });
      }
    }
  }

  
  resetPassword()
  {
    var request={
      email: this.Email,
      friend: this.firstCtrl,
      pet: this.secondCtrl,
      password: this.Password
    }
    this.httpservice.postServiceCall("/forgotpassword",request)
    .subscribe((result: any) => {
      if (result.status) {

        const dialogRef = this.dialog.open(MessageComponent, {
          data: {
            type: 'C',
            title: 'Password Reset Successful!',
            message: 'Please try signing in again!',
            duration: 3000
          }
        });
        dialogRef.afterClosed().subscribe(result => {
          this.router.navigate(['/signin'])
        })
      }
      else {
        this.dialog.open(MessageComponent, {
          data: {
            type: 'E',
            title: 'System Error',
            message: result.message,
          }
        });
      }
    },
      (error: any) => {
        this.dialog.open(MessageComponent, {
          data: {
            type: 'E',
            title: 'System Error',
            message: 'Something Went Wrong. Please Try Again.',
          }
        });
      });

  }

}

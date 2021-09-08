/*
 * Author: Misbah Pathan
 * Email id: ms358232@dal.ca
*/

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpService } from 'src/app/services/httpservice.service';
import { DataService } from 'src/app/services/dataservice.service';
import { MessageComponent } from '../message/message.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent implements OnInit {


  SigninForm!: FormGroup;

  Email: string = "";
  Password: string = "";
  invalidUser:boolean= false;
  constructor(private dataservice: DataService ,private formBuilder: FormBuilder, private dialog: MatDialog, private router: Router, private httpservice: HttpService) { }

  ngOnInit(): void {
    this.SigninForm = this.formBuilder.group({
      Email: ['', [Validators.required, Validators.pattern("([a-zA-Z0-9-_.]+@dal.ca)")]],
      Password: ['', [Validators.required]],
    });
  }

  gotoRegister(){
    this.router.navigate(['/signup'])
  }


  signin(){
    var request ={
      email: this.Email,
      password: this.Password
    }
    this.httpservice.postServiceCall("/registration/login",request)
    .subscribe((result: any)=>{
      if(result.status){
        this.dataservice.setLoggedInUser(result)
        this.router.navigate(['/main/announcement'])
      }
      else {
        this.dialog.open(MessageComponent, {
          data: {
            type: 'W',
            title: 'Incorrect Credentials',
            message: "Incorrect email/password. Please try again!",
          }
        });

      }

    },(error: any)=>{
      console.log(error)
      this.dialog.open(MessageComponent, {
        data: {
          type: 'E',
          title: 'Incorrect Credentials',
          message: "Incorrect email/password. Please try again!",
        }
      });

    })
  }

  gotoForgotPassword()
  {
    this.router.navigate(['/forgotPassword'])
  }


}

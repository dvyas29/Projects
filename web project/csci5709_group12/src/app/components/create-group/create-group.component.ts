// <!-- Mohammed Hamza Jasnak mh342039@dal.ca -->
import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { GroupFormation, Mentee } from 'src/app/models/groupFormation.model';
import { HttpService } from 'src/app/services/httpservice.service';
import { UtilityService } from 'src/app/services/utilityservice.service';
import { MessageComponent } from '../message/message.component';

@Component({
  selector: 'app-create-group',
  templateUrl: './create-group.component.html',
  styleUrls: ['./create-group.component.css']
})
export class CreateGroupComponent implements OnInit {
  groupData: GroupFormation
  criteria:string = "faculty"
  menteeList: Mentee[] = [];  
  savedMentee: Mentee[] = [];
  location: string = ""
  faculty: string = ""
  iscriteriaValueChanged: boolean = false
  criteriaValue: string = ""
  CreateGroupForm!: FormGroup;

  constructor(private util: UtilityService, private dialogRef: MatDialogRef <CreateGroupComponent >, private formBuilder: FormBuilder, private dialog: MatDialog, private httpservice: HttpService, @Inject(MAT_DIALOG_DATA) public data: any) { }

  //List of mentees will be fecthed if the popup is opend in Edit mode
  ngOnInit(): void {
    this.util.isLoader =false
    this.CreateGroupForm = this.formBuilder.group({
      Faculty: ['', [Validators.required]],
      CampusLocation: ['', [Validators.required]],
      GroupName: ['', [Validators.required]],
    });

    if(this.data.isEdit){
      this.util.isLoader =true

      this.httpservice.getServiceCall("/group-management/mentees/"+this.data.groupData._id)
      .subscribe((result: any)=>{
        this.util.isLoader =false

        if(result.status){
          this.groupData = new GroupFormation()
          this.groupData._id = this.data.groupData._id
          this.groupData.faculty = this.data.groupData.faculty
          this.groupData.groupName = this.data.groupData.groupName
          this.groupData.location = this.data.groupData.location
          this.location = this.data.groupData.location
          this.faculty = this.data.groupData.faculty

          for (let mentee in result.data){
            let temp = result.data[mentee]
            temp.checked = true
            this.menteeList.push(temp)
            this.savedMentee.push(temp)
            this.groupData.mentee.push({menteeId: temp._id, flag: ""})
          }
        }
        else{
          console.log(result)
          this.dialog.open(MessageComponent, {
            data: {
              type: 'E',
              title: 'System Error',
              message: "Something went wrong. Please try again!",
            }
          });
        }
      },(error: any)=>{
        this.util.isLoader =false

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
    else{
    this.groupData = new GroupFormation()
    }
  }

  //whenever the criteria value is changed the value is changed
  criteriaValueChanged(criteria: string, value: string){
    this.criteriaValue = value
    if(criteria == this.criteria){
      this.menteeList = []
      this.savedMentee.forEach(o=>{ 
        if(criteria == 'faculty'){
          if(o.faculty == value || value == "ALL"){
            let temp = JSON.parse(JSON.stringify(o))
            this.menteeList.push(temp)
            this.updateMentee(true, o._id)
          }
        }
        else{
          if(value == o.campusLocation  || value == "ALL"){
            let temp = JSON.parse(JSON.stringify(o))
            this.menteeList.push(temp)
            this.updateMentee(false, o._id)

          }  
        }
      })
    }
  }

  // this method will be used to fetch mentees based on the search criteria
  search(){
    var value;
    if(this.criteria == "faculty"){ value = this.groupData.faculty}
    else if(this.criteria == "location"){value = this.groupData.location}
    this.util.isLoader =true
    this.httpservice.getServiceCall("/group-management/mentees/"+this.criteria+"/"+value)
    .subscribe((result:  any)=>{
      this.util.isLoader =false

      if(result.status){
        var temp :any[] = []
        this.menteeList.forEach(o=>{ 
          if(o.checked){
            temp.push(o)
          }
        })
        this.menteeList = temp
        for(let i in result.data){
          if(temp.findIndex((o)=>{return o._id == result.data[i]._id}) < 0){
            this.menteeList.push(result.data[i])
          }
        }
      }
      else{
        this.dialog.open(MessageComponent, {
          data: {
            type: 'W',
            title: 'System Message',
            message: "No un-assigned mentees found.",
          }
        });
        }
    },(error: any)=>{ 
      this.util.isLoader =false

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

  updateMentee(checked: any, menteeId: any){
    if(this.savedMentee.findIndex(o=>{ return o._id == menteeId}) != -1 && this.groupData.mentee){
      if(checked){
        this.groupData.mentee[this.groupData.mentee.findIndex(o=>{ return o.menteeId == menteeId})].flag = ""
            }
      else{
        this.groupData.mentee[this.groupData.mentee.findIndex(o=>{ return o.menteeId == menteeId})].flag = "R"
      }
    }
    else{
      if(checked){
        this.groupData.mentee.push({menteeId: menteeId, flag: "A"})
      }
      else{
        if(this.groupData.mentee){
          this.groupData.mentee.splice(this.groupData.mentee.findIndex(o => {o.menteeId = menteeId}), 1);
        }
      }
    }
  }

  save(groupFrom: NgForm){
    if(groupFrom.invalid){
      return;
    }
      console.log("valid")
    if(this.data.isEdit){
      this.edit();
    }
    else{
      this.create();
    }
  
  }

  // this method is used to create a new group
  create(){
    this.util.isLoader =true

    this.httpservice.postServiceCall("/group-management/create", this.groupData)
    .subscribe((result: any)=>{
      this.util.isLoader =false

      if(result.status){
        console.log(result)
        this.dialogRef.close()
        this.dialog.open(MessageComponent, {
          data: {
            type: 'C',
            title: 'Success!',
            message: "Group Created Successfully ",
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
      this.util.isLoader =false

      this.dialog.open(MessageComponent, {
        data: {
          type: 'E',
          title: 'System Error',
          message: "Something went wrong. Please try again!",
        }
      });
  })
  }

  // this method is used to save the changes to existing group
  edit(){
    this.util.isLoader =true
    this.httpservice.putServiceCall("/group-management/update", this.groupData)
    .subscribe((result: any)=>{
      this.util.isLoader =false

      if(result.status){
        console.log(result)
        this.dialogRef.close()
        this.dialog.open(MessageComponent, {
          data: {
            type: 'C',
            title: 'Success!',
            message: "Group Updated Successfully",
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
      this.util.isLoader =false

      this.dialog.open(MessageComponent, {
        data: {
          type: 'E',
          title: 'System Error',
          message: "Something went wrong. Please try again!",
        }
      });

    })

  }

  // this method is used to delete the group
  delete(){

    this.util.isLoader =true

    this.httpservice.deleteServiceCall("/group-management/delete/"+this.groupData._id, {})
    .subscribe((result: any)=>{
      this.util.isLoader =false

      if(result.status){
        console.log(result)
        this.dialogRef.close()
        this.dialog.open(MessageComponent, {
          data: {
            type: 'C',
            title: 'Success!',
            message: "Group Deleted Successfully ",
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
      this.util.isLoader =false

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

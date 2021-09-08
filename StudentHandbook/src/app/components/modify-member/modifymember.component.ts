/**
* Author: Gurleen Saluja (gr997570@dal.ca)
*/
import { Component, OnInit, ViewChild, Input, DoCheck } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, MatSortHeader } from '@angular/material/sort';
import { FormControl, Validators, FormBuilder } from '@angular/forms';
import { UtilityService } from '../../services/utilityservice.service';
import { RequesterDetailsModalComponent } from '../request-list/requester-details-modal/requester-details-modal.component';
import { ModifymemberService } from '../../services/modifymember.service';
import { PeerMentorshipRegistrationModel } from 'src/app/models/peer-mentorship-registration.model';
import { MessageComponent } from '../message/message.component';
import { HttpService } from 'src/app/services/httpservice.service';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-modifymember',
  templateUrl: './modifymember.component.html',
  styleUrls: ['./modifymember.component.css']
})
export class ModifymemberComponent implements OnInit, DoCheck {

  displayedColumns: string[] = ['name', 'email', 'requestType', 'requestStatus', 'date'];

  memberData: PeerMentorshipRegistrationModel[];

  dataSource: MatTableDataSource<PeerMentorshipRegistrationModel>;

  @ViewChild(MatPaginator) paginator: MatPaginator;

  @ViewChild(MatSort) sort: MatSort;

  modifyMemberForm: any;

  isSubmitted: boolean = false;

  success: boolean = false;

  dialogValue: boolean;

  index: number;

  @Input() isRecordFound: boolean = false;

  noRecordsFound: boolean = true;

  constructor(private fb: FormBuilder, public util: UtilityService, private dataservice: ModifymemberService,
    private httpservice: HttpService, private dialog: MatDialog){
    this.modifyMemberForm = this.fb.group({
      email: ["", [Validators.pattern('[a-z0-9._%+-]+@(dal)+\\.(ca)')]]
    });
  }

  /* Sets section title. */
  ngOnInit(){
    this.util.sectionTitle="Modify Access";
    this.getRequestList();
  }

  /* Fetches members whose accounts have been modified in the past. */
  getRequestList(){
    this.httpservice.getServiceCall('/modify-member/')
    .subscribe((result:any)=>{
      if(result.status){
        this.noRecordsFound = false;
        this.memberData = result.data;
        this.setDataSource();
      }
    });
  }

  /* Sets data to be rendered in the table component. */
  setDataSource(){
    this.dataSource = new MatTableDataSource<PeerMentorshipRegistrationModel>(this.memberData);
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    if(this.dataSource.data.length === 0){
      this.dialog.open(MessageComponent, {
        data: {
          type: 'E',
          message: 'No Records Found.',
          duration: 1000
        }
      });
    }
  }

  /* Searches user. If user is found then opens a popup else displays error. */
  getUser(email: string){
    this.httpservice.getServiceCall('/peer-mentorship-registration/'+email)
    .subscribe((result:any)=>{
      if(result.status && result.data){
        if(result.data.isRegistered){
          this.memberData = result.data;
          const dialogRef = this.dialog.open(RequesterDetailsModalComponent, {
            data: this.memberData,
            width: '400px'
          });
          this.dataservice.isRecordUpdated = false;
        }
        else{
          this.dialog.open(MessageComponent, {
            data: {
              type: 'E',
              title:'System Error',
              message: 'The user is not registered with the Peer Mentorship Program.',
              duration:3000
            }
          });
        }
      }
      else{
        this.dialog.open(MessageComponent, {
          data: {
            type: 'E',
            title:'System Error',
            message: 'The user is not registered with the Dal Student Handbook Application.',
            duration:3000
          }
        });
      }
    });
  }

  /* Checks email format otherwise displays error. */
  validate(){
    if(!this.isSubmitted){
       if (this.modifyMemberForm.controls.email.hasError('pattern')){
         return 'Invalid email format';
       }
    }
  }

  openDialog(email: string){
    this.getUser(email);
  }

  ngDoCheck(){
    if(this.dataservice.isRecordUpdated){
      this.getRequestList();
      this.dataservice.isRecordUpdated = false;
    }
  }
}

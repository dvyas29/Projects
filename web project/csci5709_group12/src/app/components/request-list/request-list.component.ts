/**
* Author: Gurleen Saluja (gr997570@dal.ca)
*/
import { Component, OnInit, ViewChild, Inject, Output } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, MatSortHeader } from '@angular/material/sort';
import { MatDialog } from '@angular/material/dialog';
import { RequesterDetailsModalComponent } from './requester-details-modal/requester-details-modal.component';
import { UtilityService } from 'src/app/services/utilityservice.service';
import { PeerMentorshipRegistrationModel } from 'src/app/models/peer-mentorship-registration.model';
import { MessageComponent } from '../message/message.component';
import { HttpService } from 'src/app/services/httpservice.service';

@Component({
  selector: 'app-request-list',
  templateUrl: './request-list.component.html',
  styleUrls: ['./request-list.component.css']
})
export class RequestListComponent implements OnInit {

  dialogValue: boolean;

  displayedColumns: string[] = ['requesterName', 'requestType', 'requestModificationDate', 'requestStatus'];

  registerUser: PeerMentorshipRegistrationModel[];

  dataSource: MatTableDataSource<PeerMentorshipRegistrationModel>;

  @ViewChild(MatPaginator) paginator: MatPaginator;

  @ViewChild(MatSort) sort: MatSort;

  constructor(public dialog: MatDialog, public util: UtilityService, private httpservice: HttpService) {
    this.getRequestList();
  }

  /* Gets the list of requests for the admin. */
  getRequestList(){
    this.httpservice.getServiceCall('/peer-mentorship-registration')
    .subscribe((result:any)=>{
      if(result.status){
        this.registerUser = result.data;
        this.setDataSource();
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
    });
  }

  /*
    Opens details of a user in a modal.
    @params index
   */
  openDialog(index: number) {
    this.registerUser[index+(this.paginator.pageIndex*this.paginator.pageSize)].mode = 'V';
    const dialogRef = this.dialog.open(RequesterDetailsModalComponent, {
      data: this.registerUser[index+(this.paginator.pageIndex*this.paginator.pageSize)],
      width: '400px'
    });
    dialogRef.afterClosed().subscribe(() => {
      this.getRequestList();
    });
  }

  /* Sets section title. */
  ngOnInit(){
    this.util.sectionTitle="Requests";
  }

  /* Sets data to be rendered in the table component. */
  setDataSource(){
    this.dataSource = new MatTableDataSource<PeerMentorshipRegistrationModel>(this.registerUser);
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }
}

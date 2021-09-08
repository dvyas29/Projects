// <!-- Mohammed Hamza Jasnak mh342039@dal.ca -->
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { HttpService } from 'src/app/services/httpservice.service';
import { MessageComponent } from '../message/message.component';

@Component({
  selector: 'app-assign-mentor',
  templateUrl: './assign-mentor.component.html',
  styleUrls: ['./assign-mentor.component.css']
})
export class AssignMentorComponent implements OnInit {

  searchKeyword:any;
  displayedColumns: string[] = ['groupName', 'faculty', 'location', 'mentor','action'];
  dataSource: any;
  @ViewChild(MatPaginator) paginator: MatPaginator;


  mentorList: any[] = []
  constructor(private dialog: MatDialog, private httpservice: HttpService) { }

  ngOnInit(): void {
    //get the groups data
    this.httpservice.getServiceCall("/group-management/groups")
    .subscribe((result: any)=>{
      if(result.status){
        console.log(result)
        var temp = new MatTableDataSource(result.data);
        this.dataSource = temp;

        // get the list of mentors for the dropdown
        this.httpservice.getServiceCall("/group-management/mentors")
        .subscribe((result: any)=>{
          if(result.status){
            console.log(result)
            this.mentorList = result.data
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

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }


  cancel(name: any){
    console.log(this.dataSource)
    var index = this.dataSource.filteredData.findIndex((o: any) => o.name == name)
    this.dataSource.filteredData[index].oldMentorValue = this.dataSource.filteredData[index].mentor
  }

  save(obj:any){
    //Save call for assign mentor
    this.httpservice.postServiceCall("/group-management/assign-mentor", obj)
    .subscribe((result: any)=>{
      console.log(result)

      if(result.status){
        // fetch the groups list to fetch new groups and reflect the latest canges in the grid.
        this.httpservice.getServiceCall("/group-management/groups")
        .subscribe((result: any)=>{
          if(result.status){
            console.log(result)
            var temp = new MatTableDataSource(result.data);
            this.dataSource = temp;
            this.dialog.open(MessageComponent, {
              data: {
                type: 'C',
                title: 'Success',
                message: "Mentor Successfully Assigned",
                duration: 2000
              }
            });  
    
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
        this.dialog.open(MessageComponent, {
          data: {
            type: 'E',
            title: 'System Error',
            message: "Something went wrong. Please try again!",
          }
        });  
      }
    },(error: any)=>{
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

  getMentorName(id: any){
    let index = this.mentorList.findIndex(o=>{return o._id == id})
    if(index <0 ){
      return "un-assigned";
    }
    else {
      return this.mentorList[index].name
    }
  }
}

import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DataService } from 'src/app/services/dataservice.service';
import { HttpService } from 'src/app/services/httpservice.service';
import { UtilityService } from 'src/app/services/utilityservice.service';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
  announcementAccod: any
  allUsers:any
  constructor(private httpservice: HttpService, public utilityservice: UtilityService, private dataservice: DataService, private router: Router) { }

  ngOnInit(): void {
    if(!this.dataservice.loggedInUser || (this.dataservice.loggedInUser && !this.dataservice.loggedInUser.status)){
      this.router.navigateByUrl('/signin')
    }

  this.httpservice.getServiceCall('/peer-mentorship-registration/getApprovedUser/' + this.dataservice.loggedInUser.data.email)
  .subscribe( (result:any)=>{
    if(result.status && result.data){
      this.dataservice.peerMentorshipDetails= result.data;
      this.dataservice.loggedInUser.data.role= this.dataservice.peerMentorshipDetails.role;
    }
  })
  }
}

// <!-- Mohammed Hamza Jasnak mh342039@dal.ca -->
import { Component, OnInit } from '@angular/core';
import { UtilityService } from 'src/app/services/utilityservice.service';

@Component({
  selector: 'app-group-management',
  templateUrl: './group-management.component.html',
  styleUrls: ['./group-management.component.css']
})
export class GroupManagementComponent implements OnInit {
  selectedIndex: any = 0;
  constructor(private utilityservice : UtilityService) { }

  ngOnInit(): void {
    this.utilityservice.sectionTitle = "Manage Groups";
  }

  change(){
    //alert(this.selectedIndex)
  }
}

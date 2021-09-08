import { Component, OnInit } from '@angular/core';
import { UtilityService } from '../../services/utilityservice.service';

@Component({
  selector: 'app-student-wall',
  templateUrl: './student-wall.component.html',
  styleUrls: ['./student-wall.component.css']
})
export class StudentWallComponent implements OnInit {

  constructor(public util: UtilityService) { }

  ngOnInit(): void {
    this.util.sectionTitle = "Student Wall";
  }

}

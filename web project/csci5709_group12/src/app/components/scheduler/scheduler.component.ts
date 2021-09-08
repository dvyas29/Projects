import { Component, OnInit } from '@angular/core';
import { UtilityService } from '../../services/utilityservice.service';

@Component({
  selector: 'app-scheduler',
  templateUrl: './scheduler.component.html',
  styleUrls: ['./scheduler.component.css']
})
export class SchedulerComponent implements OnInit {

  constructor(public util: UtilityService) { }

  ngOnInit(): void {
    this.util.sectionTitle = "Scheduler";
  }

}

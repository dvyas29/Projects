import { Component } from '@angular/core';
import { UtilityService } from './services/utilityservice.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Student Handbook';
  constructor(public utilityservice: UtilityService){}

}

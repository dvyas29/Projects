import { Component, OnInit } from '@angular/core';
import { UtilityService } from 'src/app/services/utilityservice.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-faq',
  templateUrl: './faq.component.html',
  styleUrls: ['./faq.component.css']
})
export class FaqComponent implements OnInit {

  constructor(private utilityService:  UtilityService, private router: Router) { }


  ngOnInit(): void {
    this.utilityService.sectionTitle = "Frequently Asked Questions"
  }

}

/* 
 * Author: Mansi Singh 
 * Email id: mn518448@dal.ca
*/

import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { MessageComponent } from '../components/message/message.component';

@Injectable({
  providedIn: 'root'
})

export class RateMentorserviceService {

  constructor(private dialog: MatDialog, private router: Router) { }

}

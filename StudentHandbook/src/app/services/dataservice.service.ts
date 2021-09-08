import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class DataService {
  peerMentorshipDetails:any;
  role:any
  loggedInUser: any
  isAdmin: boolean= false

  constructor(private router: Router) { }

  setLoggedInUser(obj: any){
    this.loggedInUser = obj;
    this.isAdmin = (this.loggedInUser.data.role == "ADMIN")
  }

  logout(){
    this.loggedInUser.status = false
    this.isAdmin = false
    this.router.navigateByUrl('/signin')
  }
  
}

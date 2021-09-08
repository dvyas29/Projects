import { Injectable } from '@angular/core';
import { AbstractControl, ValidationErrors } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class UtilityService {

  constructor() { }
  sectionTitle:string = "";
  modalTitle:any;
  accordionDetails : any ={};
  opened :boolean = false;
  isViewMyAnnouncementControlsVisible: boolean= false;
  isLoader: boolean = false
  getaccordionDetails(){
   return this.accordionDetails;
  }
  setaccordionDetails(obj:any){
    this.accordionDetails = obj;
  }
  sidenavWidth = 5;
  sidenavMargin = 70;
  pinned: boolean = false;
  isDefaultSized: boolean = true;
  toggleshowIcon(){
    this.opened = !this.opened;
  }
  open() {
		this.sidenavWidth = 19;
    this.sidenavMargin = 270;
    this.opened = true;
	 }
	 close() {
    if(!this.pinned){
  		this.sidenavWidth = 5;
      this.sidenavMargin = 70;
    }
    this.opened = false;
}
  pin(status:boolean){
    this.pinned  = status;
    if (status){
    this.opened = true;
    }
    else{
    }
  }

/* 
 * Author: Mansi Singh 
 * Email id: mn518448@dal.ca
*/

// this code has been written by referencing https://www.codegrepper.com/code-examples/javascript/angular+for+validation+ignore+whitespaces
// this method handles whitespace validations in the input
  cannotContainSpace(control: AbstractControl) : ValidationErrors | null {
    const isWhitespace = (control.value || '').trim().length === 0;
    const isValid = !isWhitespace;
    return isValid ? null : { cannotContainSpace: true };
  }
  
  setViewMyAnnouncementPage(){
    this.isViewMyAnnouncementControlsVisible = ! this.isViewMyAnnouncementControlsVisible;
  }

}

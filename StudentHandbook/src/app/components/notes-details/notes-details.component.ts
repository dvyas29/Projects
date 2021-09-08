import { ChangeDetectorRef, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { COMMA, ENTER, SPACE } from '@angular/cdk/keycodes';
import { FormControl } from '@angular/forms';
import { MatChipInputEvent } from '@angular/material/chips';
import { Router } from '@angular/router';
import { NoteModel } from 'src/app/models/notes.model';
import { NotesService } from 'src/app/services/notesdata.service';
import { MessageComponent } from '../message/message.component';
import { MatDialog } from '@angular/material/dialog';
import { HttpService } from 'src/app/services/httpservice.service';
import { DataService } from 'src/app/services/dataservice.service';

@Component({
  selector: 'app-notes-details',
  templateUrl: './notes-details.component.html',
  styleUrls: ['./notes-details.component.css']
})
export class NotesDetailsComponent implements OnInit {
  note: NoteModel = new NoteModel();
  separatorKeysCodes: number[] = [ENTER, COMMA, SPACE];
  TitleFormControl = new FormControl();
  tagFormControl = new FormControl();
  tags: string[] = [];
  isCreate : boolean=false; 
  @ViewChild('tagInput') tagInput!: ElementRef<HTMLInputElement>;

  constructor(private userdataservice: DataService, private httpservice: HttpService, private dialog: MatDialog, private ref: ChangeDetectorRef, private dataservice: NotesService, private router: Router) {
  }

  ngOnInit(): void {
    this.getNote();
    this.ref.detectChanges();
  }

  //get the data for the selected note
  getNote() {
    let temp = this.dataservice.getNote();
    if (temp) {
      this.note = temp;
      this.isCreate = true;

    }
    else {
      this.note = new NoteModel();
      console.log(this.userdataservice.loggedInUser.data._id)
      this.note.createdByID = this.userdataservice.loggedInUser.data._id
    }
  }

  goBackToNotes() {
    this.dataservice.setNote(null);
    this.router.navigate(['/main/notes']);
  }

  addTag(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();
    if (value) {
      this.note.tags.push(value);
    }
    event.value = "";
    this.tagFormControl.setValue(null);
  }

  removeTag(tag: string): void {
    const index = this.note.tags.indexOf(tag);

    if (index >= 0) {
      this.note.tags.splice(index, 1);
    }
  }

  saveNote() {
    if (this.note._id == -1) {
      this.postNote()
    }
    else {
      this.putNote()
    }
  }

  // this method is used to save the note in DB
  postNote(){
    this.httpservice.postServiceCall('/notes', this.note)
    .subscribe( (result:any)=>{
      console.log(result)
      if(result.status){
        this.router.navigate(['/main/notes']);
        this.dialog.open(MessageComponent, {
          data: {
            type: 'C',
            title:'Added',
            message: result.message,
            duration:2000
          }
        });    
      }
      else{
        this.dialog.open(MessageComponent, {
          data: {
            type: 'E',
            title:'System Error',
            message: result.message,
          }
        });
  
      }
    },
    (error:any)=>{
      this.dialog.open(MessageComponent, {
        data: {
          type: 'E',
          title:'System Error',
          message: 'Something Went Wrong. Please Try Again.',
        }
      });

    })
  }

  // this method is used to update the note data
  putNote(){
    this.httpservice.putServiceCall('/notes/'+ this.note._id, this.note)
    .subscribe( (result:any)=>{
      console.log(result)
      if(result.status){
        this.dialog.open(MessageComponent, {
          data: {
            type: 'C',
            title:'Updated',
            message: result.message,
            duration:2000
          }
        });    
      }
      else{
        this.dialog.open(MessageComponent, {
          data: {
            type: 'E',
            title:'System Error',
            message: result.message,
          }
        });
  
      }
    },
    (error:any)=>{
      this.dialog.open(MessageComponent, {
        data: {
          type: 'E',
          title:'System Error',
          message: 'Something Went Wrong. Kindly Refresh the Page.',
        }
      });

    })
  }

  // this method is used to delete the note
  deleteNote() {
    let index = this.dataservice.getNoteIndex(this.note._id);
    if (index > -1) {
      this.dataservice.deleteNote(this.note._id);

    }
  }

}

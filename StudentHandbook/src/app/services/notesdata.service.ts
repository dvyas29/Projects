import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { MessageComponent } from '../components/message/message.component';
import { NoteModel } from '../models/notes.model';
import { HttpService } from './httpservice.service';

@Injectable({
  providedIn: 'root'
})
export class NotesService {

  note:any;
  allNotes: NoteModel[] = [];
  constructor(private httpservice: HttpService, private dialog: MatDialog, private router: Router) { 
  }

  setNote(note:any){
    this.note = note;
  }

  setAllNote(notes:NoteModel[]){
    this.allNotes = notes;
  }

  getNote(){
    return this.note;
  }

  getNotes(){
    return this.allNotes;
  }

  deleteNote(id: any){
    const dialogRef = this.dialog.open(MessageComponent, {
      data: {
        type: 'W',
        title:'Are you sure?',
        message: 'You will not be able to recover this note!',
        buttonText:'Yes, delete it!',
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result.data == "Y"){
        this.httpservice.deleteServiceCall('/notes/' + id, {})
        .subscribe( (result:any)=>{
          if(result.status){
            let index = this.getNoteIndex(id)
            if(index != -1){
            this.allNotes.splice(index,1);
          }
            this.router.navigate(['/main/notes']);
            this.dialog.open(MessageComponent, {
              data: {
                type: 'C',
                title:'Deleted',
                message: 'Note deleted successfully.',
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
    });
  }

  
  saveNote(index:any, obj:any){
    this.allNotes[index] = obj;
    this.dialog.open(MessageComponent, {
      data: {
        type: 'C',
        title:'Saved',
        message: 'Note saved successfully.',
        duration:2000
      }
    });

  }

  addNote(obj: any){
    this.allNotes.push(obj);
    this.dialog.open(MessageComponent, {
      data: {
        type: 'C',
        title:'Added',
        message: 'New note added successfully.',
        duration:2000
      }
    });

  }
  getNoteIndex(id:number){
    return this.allNotes.map(o => o._id).indexOf(id);
  }
}

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.css']
})
export class MessageComponent implements OnInit {

  constructor(private messageDialogRef: MatDialogRef<MessageComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
    if ((this.data.type === 'C' || this.data.type === 'E') && this.data.duration) {
      setTimeout(() => { this.messageDialogRef.close() }, this.data.duration)
    }
  }

  close(response: any) {
    this.messageDialogRef.close({
      data: response
    });
  }
}

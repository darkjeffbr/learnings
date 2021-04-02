import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { Photo } from '../../photo/photo';
import { PhotoService } from '../../photo/photo.service';

@Component({
  selector: 'cp-photos',
  templateUrl: './photos.component.html',
  styleUrls: ['./photos.component.css']
})
export class PhotosComponent implements OnChanges {

  @Input() photos: Photo[] = [];

  rows = [];

  constructor() { }

  ngOnChanges(changes: SimpleChanges) {
    if(changes.photos){
      this.rows = this.groupColumns(this.photos);
    }
  }

  groupColumns(photos: Photo[]){
    const newRows = [];

    console.log(photos.toString());

    for(let index = 0; index < photos.length; index+=3){
      console.log(index);
      console.log(index+3);
      console.log(photos.slice(index, index+3));
      newRows.push(photos.slice(index, index+3));
    }
    return newRows;
  }

}

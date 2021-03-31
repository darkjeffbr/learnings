import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, of } from "rxjs";
import { Photo } from "./photo";

@Injectable({providedIn: 'root'})
export class PhotoService {

    constructor(private http:HttpClient){}

    getCatPhotos(amount:number): Observable<Photo[]> {
        let photos:Photo[] = [];
        for(let i = 0; i<amount; i++){
            this.http.get<Object>('https://aws.random.cat/meow').subscribe(data => {
                photos.push({
                    url: data.file,
                    description: `cat {i}`
                });
            });
        }

        return of(photos);
        
    }
}
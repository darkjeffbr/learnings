import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styles: []
})
export class AppComponent implements OnInit {
  
  colors = [
    'Red',
    'Orange',
    'Yellow',
    'Green',
    'Blue',
    'Cyan',
    'Purple',
    'White',
    'Black',
    'Brown',
    'Magenta',
    'Tan',
    'Olive',
    'Maroon',
    'Navy',
    'Aquamarine',
    'Turquoise',
    'Silver',
    'Lime',
    'Teal',
    'Indigo',
    'Violet',
    'Pink',
    'Gray'
  ];

  filtered = this.colors;

  myForm: FormGroup;

  constructor(private fb: FormBuilder){}

  ngOnInit(): void {
    /*
    // Using form group constructor
    this.myForm = new FormGroup({
      name: new FormControl('Jeff'),
      email: new FormControl(''),
      message: new FormControl('')
    });
    */

    // using form builder
    this.myForm = this.fb.group({
      name: ['Jeff', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      message: ['', [Validators.required, Validators.minLength(5)]]
    });
  }

  onSubmit(form: FormGroup) {
    console.log('Valid? ', form.valid);
    console.log('Name ', form.value.name);
    console.log('Email ', form.value.email);
    console.log('Message ', form.value.message);
  }

  filter(event){
    if (event.target.value.length == 0){
      this.filtered = this.colors;
      return;
    }

    this.filtered = this.colors.filter(e => e.includes(event.target.value));
  }

  selected(event){
    console.log(event.target.value);
  }

}

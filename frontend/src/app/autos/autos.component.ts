import {Component} from '@angular/core';
import {Auto} from "../models/auto";
import {Observable} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Location} from "@angular/common";
import {FormBuilder, FormControl, FormGroup, NgForm} from "@angular/forms";

@Component({
  selector: 'app-autos',
  templateUrl: './autos.component.html',
  styleUrls: ['./autos.component.css']
})
export class AutosComponent {
  private url = 'http://localhost:8080/autos';
  autos: Array<Auto> = [];

  reactiveForm = new FormGroup({
    id: new FormControl(0),
    brand: new FormControl(''),
    model: new FormControl(''),
    manufactureDate: new FormControl(new Date()),
    number: new FormControl(''),
    ownerId: new FormControl(0),
  })

  constructor(
    private http: HttpClient,
    private location: Location,
    private formBuilder: FormBuilder
  ) {
  }

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  save() {
    let id = this.reactiveForm.controls.id.value;
    let brand = this.reactiveForm.controls.brand.value;
    let model = this.reactiveForm.controls.model.value;
    let manufactureDate = this.reactiveForm.controls.manufactureDate.value;
    let number = this.reactiveForm.controls.number.value;
    let ownerId = this.reactiveForm.controls.ownerId.value;
    if (!brand || !model || !manufactureDate
      || !number || !ownerId) {
      return;
    }
    if (!this.reactiveForm.value.id) {
      this.createAuto({brand, model, manufactureDate, number, ownerId} as Auto)
        .subscribe(auto => {
          this.autos.push(auto);
        });
    } else {
      this.updateAuto({id, brand, model, manufactureDate, number, ownerId} as Auto)
        .subscribe(auto => {
          this.autos.push(auto);
        });
    }
  }

  private createAuto(auto: Auto): Observable<Auto> {
    return this.http.post<Auto>(this.url, auto, this.httpOptions);
  }

  private updateAuto(auto: Auto): Observable<Auto> {
    return this.http.put<Auto>(this.url + '/' + auto.id, auto, this.httpOptions);
  }
}

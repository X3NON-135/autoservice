import {Component} from '@angular/core';
import {Duty} from "../models/duty";
import {Observable} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {FormControl, FormGroup, NgForm} from "@angular/forms";

@Component({
  selector: 'app-duties',
  templateUrl: './duties.component.html',
  styleUrls: ['./duties.component.css']
})
export class DutiesComponent {
  private url = 'http://localhost:8080/duties';
  duties: Array<Duty> = [];

  reactiveForm = new FormGroup({
    id: new FormControl(0),
    orderId: new FormControl(0),
    masterId: new FormControl(0),
    price: new FormControl(0),
    paymentStatus: new FormControl(''),
    typeOfDuty: new FormControl('')
  })

  updateStatusForm = new FormGroup({
    id: new FormControl(0),
    paymentStatus: new FormControl('')
  })

  constructor(
    private http: HttpClient,
  ) {
  }

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  toNumber(value: string) {
    return Number(value);
  }

  save() {
    let id = this.reactiveForm.controls.id.value;
    let orderId = this.reactiveForm.controls.orderId.value;
    let masterId = this.reactiveForm.controls.masterId.value;
    let price = this.reactiveForm.controls.price.value;
    let paymentStatus = this.reactiveForm.controls.paymentStatus.value;
    let typeOfDuty = this.reactiveForm.controls.typeOfDuty.value;
    if (!orderId || !masterId || !price
      || !paymentStatus || !typeOfDuty) {
      return;
    }
    if (!id) {
      this.createDuty({orderId: orderId, masterId: masterId, price, paymentStatus, typeOfDuty} as Duty)
        .subscribe(duty => {
          this.duties.push(duty);
        });
    } else {
      this.updateDuty({id, masterId: masterId} as Duty)
        .subscribe(duty => {
          this.duties.push(duty);
        });
    }
  }

  createDuty(duty: Duty): Observable<Duty> {
    return this.http.post<Duty>(this.url, duty, this.httpOptions);
  }

  updateDuty(duty: Duty): Observable<Duty> {
    return this.http.put<Duty>(this.url + '/' + duty.id, duty, this.httpOptions);
  }

  updateStatus() {
    let id = this.updateStatusForm.controls.id.value;
    let paymentStatus = this.updateStatusForm.controls.paymentStatus.value;
    this.http.put<Duty>(this.url + '/' + id + '/update-status?payment_status=' + paymentStatus, this.httpOptions)
      .subscribe(duty => {
        this.duties.push(duty)
      });
  }
}

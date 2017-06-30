import { Component, OnInit } from '@angular/core';
import {FormGroup,FormControl,Validators,FormArray,FormBuilder} from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import { AlertService, DealService } from '../services/index';

@Component({
  selector: 'app-tapecracking',
  templateUrl: './tapecracking.component.html',
  styleUrls: ['./tapecracking.component.css']
})
export class TapecrackingComponent implements OnInit {
   public ngForm: FormGroup;
   public dealId;
   public loading = false;

constructor(
        private http: Http,
        private route: ActivatedRoute,
        private router: Router,
        private dealService: DealService,
        private _routeParams:ActivatedRoute,
        private alertService: AlertService
) {
}

  ngOnInit() {
    this.ngForm = new FormGroup({
      loans: new FormArray([])
    });
this._routeParams.params.subscribe(params => {
    this.dealId = +params['dealId'];
    });
  }

  addRow(){
   // console.log(this.ngForm);
    (<FormArray>this.ngForm.get("loans")).push(new FormGroup({
     "dealId":new FormControl(this.dealId),
      "loanId":new FormControl(null,[Validators.required]),
      "borrowerName":new FormControl(null,Validators.required),
      "ssn":new FormControl(null,Validators.required),
      "loanType":new FormControl(null,Validators.required),
      "loanAmount":new FormControl(null,Validators.required),
    }));
   // console.log(this.ngForm.value);
  }

  addLoans(){
    //console.log(this.ngForm.value.loans);
    this.dealService.createLoan(this.ngForm.value.loans)
    .subscribe(
        data => {
            this.alertService.success('Loan save successful', true);
            this.router.navigate(['taskDashboard']);
        },
        error => {
            this.alertService.error(error);
            this.loading = false;
        });

  }
}
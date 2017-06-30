import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import { TapecrackingComponent } from '../tapeCracking/index';
import { AlertService, DealService } from '../services/index';
import { Deal } from '../models/index';
import { Observable } from 'rxjs/Observable';
import 'rxjs/Rx';
import { FormGroup, FormArray, FormControl } from "@angular/forms";
import { Review } from '../models/review';

@Component({
    moduleId: module.id,
    styleUrls: ['dealsetup.component.css'],
    templateUrl: 'dealsetup.component.html'
})

export class DealSetupComponent implements OnInit {

    reviewTypes: Array<String> = ['Credit', 'Compliance', 'Loan Mod', 'Securitization'];
    ngForm: FormGroup;
    reviewTypesSelected: Array<Review>;
    //Review[];
    deal: Deal;
    defaultPrioritie = "MEDIUM";
    public priorities: string[] = [];
    public loading = false;

    constructor(
        private http: Http,
        private route: ActivatedRoute,
        private router: Router,
        private dealService: DealService,
        private alertService: AlertService) {
        this.getReviewTypes()
            .subscribe(
            data => { { this.reviewTypes = data } },
            err => console.log('get error: ', err)
            )
        this.getPriorityTypes()
            .subscribe(
            data => { { this.priorities = data } },
            err => console.log('get error: ', err)
            )
    }
    ngOnInit() {
        this.ngForm = new FormGroup({
            'dealName': new FormControl(null),
            'clientName': new FormControl(null),
            'dealDate': new FormControl(null),
            'priority': new FormControl(null),
            'rTypes': new FormArray([])
        });
        for (let rtype of this.reviewTypes) {
            (<FormArray>this.ngForm.get('rTypes')).push(new FormGroup({
                name: new FormControl(rtype),
                value: new FormControl(false)
            }));
        }
    }
    onSubmit() {
        this.reviewTypesSelected = [];
        //let some = { reviewType: 'something' }
        for (let rtype of this.ngForm.value.rTypes) {
            if (rtype.value) {               
                this.reviewTypesSelected.push(new Review(rtype.name));
            }
        }
        //console.log(this.reviewTypesSelected);
        /*let reviewType=[];
        for(let review of this.reviewTypesSelected){
            console.log(review);
            reviewType.push({'reviewTypes':review});
        }
console.log(reviewType);*/
         
        this.deal = new Deal(this.ngForm.value.dealName, 
                               this.ngForm.value.clientName, 
                               this.ngForm.value.dealDate, 
                               this.ngForm.value.priority, 
                               this.reviewTypesSelected, 
                               null);
        
       // console.log(JSON.stringify(this.deal));
        this.dealService.create(this.deal)
            .subscribe(
            data => {
                this.alertService.success('Deal setup successful', true);
               // console.log(data);
                this.router.navigate(['tapeCracking', data.json().dealId]);
                // this.router.navigate(['tapeCracking', 1]);
            },
            error => {
                this.alertService.error(error);
                this.loading = false;
            });
    }
    /*submit() {
        this.loading = true;
        console.log(this.model);
        this.dealService.create(this.model)
            .subscribe(
            data => {
                //console.log(data);
                this.alertService.success('Deal setup successful', true);
                this.router.navigate(['tapeCracking', data.json().dealId]);
            },
            error => {
                this.alertService.error(error);
                this.loading = false;
            });
    }*/
    getReviewTypes(): Observable<any> {
        return this.dealService.getAllReviewTypes();
    }

    getPriorityTypes(): Observable<any> {
        return this.dealService.getAllPriorityTypes();
    }
    taskDashBoard(event) {
        this.router.navigate(['taskDashboard']);
        event.preventDefault();
    }

}







import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { BalanceWheel } from './balance-wheel.model';
import { BalanceWheelPopupService } from './balance-wheel-popup.service';
import { BalanceWheelService } from './balance-wheel.service';
import { Category, CategoryService } from '../category';

@Component({
    selector: 'jhi-balance-wheel-dialog',
    templateUrl: './balance-wheel-dialog.component.html'
})
export class BalanceWheelDialogComponent implements OnInit {

    balanceWheel: BalanceWheel;
    isSaving: boolean;

    categories: Category[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private balanceWheelService: BalanceWheelService,
        private categoryService: CategoryService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.categoryService.query()
            .subscribe((res: HttpResponse<Category[]>) => { this.categories = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.balanceWheel.id !== undefined) {
            this.subscribeToSaveResponse(
                this.balanceWheelService.update(this.balanceWheel));
        } else {
            this.subscribeToSaveResponse(
                this.balanceWheelService.create(this.balanceWheel));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<BalanceWheel>>) {
        result.subscribe((res: HttpResponse<BalanceWheel>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: BalanceWheel) {
        this.eventManager.broadcast({ name: 'balanceWheelListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCategoryById(index: number, item: Category) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-balance-wheel-popup',
    template: ''
})
export class BalanceWheelPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private balanceWheelPopupService: BalanceWheelPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.balanceWheelPopupService
                    .open(BalanceWheelDialogComponent as Component, params['id']);
            } else {
                this.balanceWheelPopupService
                    .open(BalanceWheelDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { BalanceWheel } from './balance-wheel.model';
import { BalanceWheelPopupService } from './balance-wheel-popup.service';
import { BalanceWheelService } from './balance-wheel.service';

@Component({
    selector: 'jhi-balance-wheel-delete-dialog',
    templateUrl: './balance-wheel-delete-dialog.component.html'
})
export class BalanceWheelDeleteDialogComponent {

    balanceWheel: BalanceWheel;

    constructor(
        private balanceWheelService: BalanceWheelService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.balanceWheelService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'balanceWheelListModification',
                content: 'Deleted an balanceWheel'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-balance-wheel-delete-popup',
    template: ''
})
export class BalanceWheelDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private balanceWheelPopupService: BalanceWheelPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.balanceWheelPopupService
                .open(BalanceWheelDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

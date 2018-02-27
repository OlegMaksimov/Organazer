import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { BalanceWheel } from './balance-wheel.model';
import { BalanceWheelService } from './balance-wheel.service';

@Injectable()
export class BalanceWheelPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private balanceWheelService: BalanceWheelService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.balanceWheelService.find(id)
                    .subscribe((balanceWheelResponse: HttpResponse<BalanceWheel>) => {
                        const balanceWheel: BalanceWheel = balanceWheelResponse.body;
                        balanceWheel.date = this.datePipe
                            .transform(balanceWheel.date, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.balanceWheelModalRef(component, balanceWheel);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.balanceWheelModalRef(component, new BalanceWheel());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    balanceWheelModalRef(component: Component, balanceWheel: BalanceWheel): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.balanceWheel = balanceWheel;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}

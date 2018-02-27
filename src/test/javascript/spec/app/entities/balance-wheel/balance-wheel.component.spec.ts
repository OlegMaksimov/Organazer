/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OrganazerTestModule } from '../../../test.module';
import { BalanceWheelComponent } from '../../../../../../main/webapp/app/entities/balance-wheel/balance-wheel.component';
import { BalanceWheelService } from '../../../../../../main/webapp/app/entities/balance-wheel/balance-wheel.service';
import { BalanceWheel } from '../../../../../../main/webapp/app/entities/balance-wheel/balance-wheel.model';

describe('Component Tests', () => {

    describe('BalanceWheel Management Component', () => {
        let comp: BalanceWheelComponent;
        let fixture: ComponentFixture<BalanceWheelComponent>;
        let service: BalanceWheelService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OrganazerTestModule],
                declarations: [BalanceWheelComponent],
                providers: [
                    BalanceWheelService
                ]
            })
            .overrideTemplate(BalanceWheelComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BalanceWheelComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BalanceWheelService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new BalanceWheel(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.balanceWheels[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

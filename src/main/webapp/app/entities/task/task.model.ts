import { BaseEntity } from './../../shared';

export const enum Status {
    'INPROGRESS',
    'OPEN',
    'CLOSE',
    'DELETE',
    'SHEDULED'
}

export const enum Repeat {
    'NOREPEAT',
    'DAYLY',
    'WEEKLY',
    'MONTHLY'
}

export class Task implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public discription?: string,
        public dateStart?: any,
        public dateEnd?: any,
        public prioritet?: number,
        public time?: number,
        public progres?: number,
        public status?: Status,
        public impotment?: boolean,
        public quick?: boolean,
        public repeat?: Repeat,
        public tasks?: BaseEntity[],
        public task?: BaseEntity,
        public subTasks?: BaseEntity[],
        public category?: BaseEntity,
    ) {
        this.impotment = false;
        this.quick = false;
    }
}

import { BaseEntity } from './../../shared';

export class Plane implements BaseEntity {
    constructor(
        public id?: number,
        public dateStart?: any,
        public dateEnd?: any,
        public discription?: string,
        public task?: BaseEntity,
    ) {
    }
}

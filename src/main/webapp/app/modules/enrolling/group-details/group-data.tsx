import { ClassType } from 'app/shared/model/enumerations/class-type.model';

export default class GroupData {
  public readonly id: number;
  public readonly startDate: Date;
  public readonly endDate: Date;
  public readonly room: string;
  public readonly building: string;
  public readonly lecturer: string;

  constructor(id: number, startDate: Date, endDate: Date, room: string, building: string, lecturer: string) {
    this.id = id;
    this.startDate = startDate;
    this.endDate = endDate;
    this.room = room;
    this.building = building;
    this.lecturer = lecturer;
  }
}

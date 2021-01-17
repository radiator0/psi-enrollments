import ClassType from './class-type';

export default class ClassUnit {
  public readonly id: number;
  public readonly startDate: Date;
  public readonly endDate: Date;
  public readonly course: string;
  public readonly classType: ClassType;
  public readonly room: string;
  public readonly building: string;

  constructor(id: number, startDate: Date, endDate: Date, course: string, classType: ClassType, room: string, building: string) {
    this.id = id;
    this.startDate = startDate;
    this.endDate = endDate;
    this.course = course;
    this.classType = classType;
    this.room = room;
    this.building = building;
  }
};

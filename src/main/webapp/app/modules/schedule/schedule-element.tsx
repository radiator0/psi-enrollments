import ClassType from './class-type';

export default class ScheduleElement {
  public readonly id: number;
  public readonly startDate: Date;
  public readonly endDate: Date;
  public readonly courseName: string;
  public readonly courseShortName: string;
  public readonly classType: ClassType;
  public readonly room: string;
  public readonly building: string;
  public readonly lecturerTitle: string;
  public readonly lecturerFirstName: string;
  public readonly lecturerSecondName: string;
  public readonly lecturerLastName: string;

  constructor(id: number, startDate: Date, endDate: Date, courseName: string, courseShortName: string, classType: ClassType, room: string, building: string,
    lecturerTitle: string, lecturerFirstName: string, lecturerSecondName: string, lecturerLastName: string) {
    this.id = id;
    this.startDate = startDate;
    this.endDate = endDate;
    this.courseName = courseName;
    this.courseShortName = courseShortName;
    this.classType = classType;
    this.room = room;
    this.building = building;
    this.lecturerTitle = lecturerTitle;
    this.lecturerFirstName = lecturerFirstName;
    this.lecturerSecondName = lecturerSecondName;
    this.lecturerLastName = lecturerLastName;
  }
};
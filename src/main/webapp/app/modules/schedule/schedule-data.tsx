import SemesterPeriod from './domain/semester-period'
import WeekType from './domain/week-type';

export default class ScheduleData {
  public readonly id: number;
  public readonly startDate: Date;
  public readonly endDate: Date;
  public readonly title: string;
  public readonly type: string;
  public readonly weekType: WeekType;
  public readonly semesterPeriod: SemesterPeriod;
  public readonly lecturerTitle: string;
  public readonly lecturerFirstName: string;
  public readonly lecturerSecondName: string;
  public readonly lecturerLastName: string;
  public readonly room: string;
  public readonly building: string;

  constructor(id: number, startDate: Date, endDate: Date, title: string,
    type: string, weekType: WeekType, semesterPeriod: SemesterPeriod,
    lecturerTitle: string, lecturerFirstName: string, lecturerSecondName: string, lecturerLastName: string,
    room: string, building: string,
    ) {
    this.id = id;
    this.startDate = startDate;
    this.endDate = endDate;
    this.title = title;
    this.type = type;
    this.weekType = weekType;
    this.semesterPeriod = semesterPeriod;
    this.lecturerTitle = lecturerTitle;
    this.lecturerFirstName = lecturerFirstName;
    this.lecturerSecondName = lecturerSecondName;
    this.lecturerLastName = lecturerLastName;
    this.room = room;
    this.building = building;
  }
};
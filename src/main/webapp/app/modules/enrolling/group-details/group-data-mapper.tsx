import ScheduleElement from 'app/shared/model/domain/dto/schedule-element';
import GroupData from './group-data';
import { APP_DATE_FORMAT_DOT } from 'app/config/constants';
import React from 'react';

export default function mapScheduleElementToGroupData(scheduleElement: ScheduleElement) {
  const lecturer = `${scheduleElement.lecturerTitle + ' '}${scheduleElement.lecturerFirstName + ' '}${
    scheduleElement.lecturerSecondName + ' '
  }${scheduleElement.lecturerLastName + ' '}`;
  return new GroupData(
    scheduleElement.id,
    scheduleElement.startDate,
    scheduleElement.endDate,
    scheduleElement.room,
    scheduleElement.building,
    lecturer
  );
}

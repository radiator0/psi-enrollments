import { translate } from 'react-jhipster';

export default function createScheduleResources() {
  return [{
    fieldName: 'type',
    title: 'Type',
    instances: [
      { id: 'Exercises', text: translate('enrollmentsApp.ClassType.Exercises'), color: '#2E57C2' },
      { id: 'Lecture', text: translate('enrollmentsApp.ClassType.Lecture'), color: '#EC407A' },
      { id: 'Laboratory', text: translate('enrollmentsApp.ClassType.Laboratory'), color: '#7E57C2' },
      { id: 'Project', text: translate('enrollmentsApp.ClassType.Project'), color: '#0E57C2' },
      { id: 'Seminar', text: translate('enrollmentsApp.ClassType.Seminar'), color: '#2E07C2' },
    ],
  }]
};
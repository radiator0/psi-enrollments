import React, { Component } from 'react';
import { connect } from 'react-redux';
import { Row } from 'reactstrap';

export type IScheduleProps = StateProps;

class Schedule extends Component<IScheduleProps> {
  render() {
    return (
      <Row>
        Schedule
      </Row>
    );
  }
}

const mapStateToProps = () => ({
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(Schedule);

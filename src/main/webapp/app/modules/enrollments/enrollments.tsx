import React, { Component } from 'react';
import { connect } from 'react-redux';
import { Row } from 'reactstrap';

export type IEnrollmentsProps = StateProps;

class Enrollments extends Component<IEnrollmentsProps> {
  render() {
    return (
      <Row>
        Enrollments
      </Row>
    );
  }
};

const mapStateToProps = () => ({
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(Enrollments);

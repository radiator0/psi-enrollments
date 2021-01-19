import React, { Component } from 'react';
import { connect } from 'react-redux';
import { Row } from 'reactstrap';

export type ISemestersProps = StateProps;

class Semesters extends Component<ISemestersProps> {
  render() {
    return (
      <Row>
        Semesters
      </Row>
    );
  }
};

const mapStateToProps = () => ({
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(Semesters);

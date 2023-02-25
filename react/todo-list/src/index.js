import React from 'react';
import ReactDOM from 'react-dom/client';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSquare, faCheckSquare } from '@fortawesome/free-regular-svg-icons';

import data from './data.json';
import './todo.css';

class TodoItem extends React.Component {
  render() {
    return <div className='todo-item'>
      <i>
        <FontAwesomeIcon icon={
          this.props.completed ?
          faCheckSquare :
          faSquare
        } />
      </i>
      <span>{this.props.title}</span>
    </div>;
  }
}

class TodoList extends React.Component {

  constructor(props) {
    super(props);
    this.state = props;
    this.addItem = this.addItem.bind(this);
  }

  addItem(e) {
    const { data } = this.props;
    if(e.key === 'Enter') {
      data.push({
        title: e.target.value
      });
      this.setState(
        data
      );
      e.target.value = ''
    }    
  }

  render() {
    return <div className='todo-list'>
      <h1>TODO List</h1>
      <input className='todo-input' id='todo-input' onKeyDown={this.addItem} />
      <div>
        {
          this.state.data.map((item, index) => {
            let completed = false;
            if(index%2===0) {
              completed=true;
            }
            return <TodoItem 
              key={item.title+index} 
              title={item.title} 
              completed={completed}
            />
          })
        }
      </div>
    </div>;
  }

}

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <TodoList data={data} />
);

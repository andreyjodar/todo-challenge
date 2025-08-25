import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Login from './pages/login/Login';
import Home from './pages/home/Home';
import PrivateRouteLayout from './components/layout/PrivateRouteLayout';
import LayoutPattern from './components/layout/LayoutPattern';
import UserForm from './pages/user-form/UserForm';

import 'primereact/resources/themes/lara-light-indigo/theme.css';
import 'primereact/resources/primereact.min.css';
import 'primeicons/primeicons.css';
import TodoPublic from './pages/todo-public/TodoPublic';
import LabelForm from './pages/label-form/LabelForm';
import LabelList from './pages/label-list/LabelList';
import TaskList from './pages/task-list/TaskList';
import TaskForm from './pages/task-form/TaskForm';

function App() {
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route element={<PrivateRouteLayout/>}>
            <Route path='/home' element={<LayoutPattern>
              <Home />
            </LayoutPattern>} />
            <Route path='/user-form' element={<LayoutPattern>
              <UserForm />
            </LayoutPattern>} />
            <Route path='/label-form' element={<LayoutPattern>
              <LabelForm />
            </LayoutPattern>}/>
            <Route path='/home/labels' element={<LayoutPattern>
              <LabelList />
            </LayoutPattern>}/> 
            <Route path='/home/labels/label-form' element={<LayoutPattern>
              <LabelForm />
            </LayoutPattern>}/>
            <Route path='/home/labels/label-form/:id' element={<LayoutPattern>
              <LabelForm />
            </LayoutPattern>}/>
            <Route path='/home/tasks' element={<LayoutPattern>
              <TaskList />
            </LayoutPattern>}/>
            <Route path='/home/tasks/task-form' element={<LayoutPattern>
              <TaskForm />
            </LayoutPattern>}/>
            <Route path='/home/tasks/task-form/:id' element={<LayoutPattern>
              <TaskForm />
            </LayoutPattern>}/>  
          </Route>          
          <Route path='/login' element={<LayoutPattern>
            <Login />
          </LayoutPattern>} />
          <Route path='/' element={<LayoutPattern>
            <TodoPublic/>
          </LayoutPattern>}/>
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
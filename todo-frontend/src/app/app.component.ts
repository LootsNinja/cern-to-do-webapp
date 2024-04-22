import {Component, OnInit} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {CommonModule, NgForOf} from "@angular/common";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, ReactiveFormsModule, HttpClientModule, CommonModule, NgForOf],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  title = 'todo-frontend';
  taskForm: FormGroup;
  categories: any[] = [];
  tasks: any[] = [];
  categoryForm: FormGroup;

  constructor(private fb: FormBuilder, private http: HttpClient) {
    this.taskForm = this.fb.group({
      taskName: ['', [Validators.required, Validators.maxLength(100)]],
      taskDescription: ['', [Validators.maxLength(500)]],
      taskCategoryName: [null],
      deadlineDate: [null],
      deadlineTime: [null]
    });

    this.categoryForm = this.fb.group({
      categoryName: ['', [Validators.required, Validators.maxLength(100)]],
      categoryDescription: ['', Validators.maxLength(500)]
    });
  }

  ngOnInit() {
    this.refresh();
  }

  refresh() {
    this.getTasks();
    this.getCategories();
  }

  onTaskSubmit() {
    if (this.taskForm.valid) {
      const taskData: any = {
        taskName: this.taskForm.value.taskName,
        taskDescription: this.taskForm.value.taskDescription
      };

      if (this.taskForm.value.deadlineDate && this.taskForm.value.deadlineTime) {
        taskData.deadline = String(this.taskForm.value.deadlineDate + ' ' + this.taskForm.value.deadlineTime);
      }

      if (this.taskForm.value.taskCategoryName) {
        taskData.taskCategoryName = this.taskForm.value.taskCategoryName;
      }

      this.http.post('http://localhost:8080/tasks', taskData).subscribe({
        next: (response) => {
          console.log('Success!', response);
        },
        error: (error) => console.error('Error!', error),
        complete: () => this.refresh()
      });
    }
  }

  getCategories() {
    this.http.get<{
      statusCode: number,
      statusMessage: string,
      response: any[]
    }>('http://localhost:8080/categories').subscribe({
      next: (data) => this.categories = data.response,
      error: (error) => console.error('Error!', error)
    });
  }

  getTasks() {
    console.log('Getting tasks...');
    this.http.get<{
      statusCode: number,
      statusMessage: string,
      response: any[]
    }>('http://localhost:8080/tasks').subscribe({
      next: (data) => this.tasks = data.response,
      error: (error) => console.error('Error!', error)
    });
  }

  deleteTask(taskId: number) {
    this.http.delete(`http://localhost:8080/tasks/${taskId}`).subscribe({
      next: () => {
        console.log('Task deleted successfully');
        // After deleting the task from the backend, you can also remove it from the tasks array in the frontend
        this.tasks = this.tasks.filter(task => task.id !== taskId);
      },
      error: (error) => console.error('Error!', error),
      complete: () => this.refresh()
    });

  }

  onCategorySubmit() {
    if (this.categoryForm.valid) {
      this.http.post('http://localhost:8080/categories', this.categoryForm.value).subscribe({
        next: () => {
          console.log('Category created successfully');
          this.categoryForm.reset();
        },
        error: (error) => console.error('Error!', error),
        complete: () => this.refresh()
      });
    }
  }

  deleteCategory(categoryId: number) {
    this.http.delete(`http://localhost:8080/categories/${categoryId}`).subscribe({
      next: () => {
        console.log('Category deleted successfully');
        // After deleting the category from the backend, you can also remove it from the categories array in the frontend
        this.categories = this.categories.filter(category => category.id !== categoryId);
      },
      error: (error) => console.error('Error!', error),
      complete: () => this.refresh()
    });
  }
}

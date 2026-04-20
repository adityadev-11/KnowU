/* ============================================
   KnowU – Student Management System – app.js
   ============================================ */

// ──────────────────────────────────────────────
// NAVIGATION
// ──────────────────────────────────────────────
const breadcrumbs = {
  dashboard: 'Dashboard',
  students: 'Students',
  attendance: 'Attendance',
  exams: 'Exam Schedule',
  classes: 'Class Schedule',
  cafeteria: 'Cafeteria',
  results: 'Results',
  library: 'Library',
  fees: 'Fee Management',
  notifications: 'Notifications',
  settings: 'Settings'
};

function navigateTo(pageId) {
  document.querySelectorAll('.page').forEach(p => p.classList.remove('active'));
  document.querySelectorAll('.nav-item').forEach(n => n.classList.remove('active'));

  const page = document.getElementById('page-' + pageId);
  if (page) page.classList.add('active');

  const navItem = document.querySelector(`.nav-item[data-page="${pageId}"]`);
  if (navItem) navItem.classList.add('active');

  document.getElementById('breadcrumb').textContent = breadcrumbs[pageId] || pageId;
}

function toggleSidebar() {
  const sidebar = document.getElementById('sidebar');
  if (window.innerWidth <= 768) {
    sidebar.classList.toggle('mobile-open');
  } else {
    sidebar.classList.toggle('collapsed');
    document.body.classList.toggle('sidebar-collapsed');
  }
}

// ──────────────────────────────────────────────
// MODALS
// ──────────────────────────────────────────────
function openModal(id)  { document.getElementById(id).classList.remove('hidden'); }
function closeModal(id) { document.getElementById(id).classList.add('hidden'); }

document.addEventListener('click', e => {
  if (e.target.classList.contains('modal-overlay')) {
    e.target.classList.add('hidden');
  }
});

// ──────────────────────────────────────────────
// TOAST
// ──────────────────────────────────────────────
function showToast(msg) {
  const t = document.getElementById('toast');
  t.textContent = msg;
  t.classList.add('show');
  setTimeout(() => t.classList.remove('show'), 3000);
}

// ──────────────────────────────────────────────
// STUDENTS DATA & TABLE
// ──────────────────────────────────────────────
const studentsData = [
  { id:'STU2481', name:'Rahul Kumar',    initials:'RK', color:'#6366f1', class:'XII – Science A', phone:'9876543210', att:96, status:'Active' },
  { id:'STU2480', name:'Priya Sharma',   initials:'PS', color:'#f43f5e', class:'XI – Commerce B', phone:'9845123456', att:88, status:'Active' },
  { id:'STU2479', name:'Arjun Mehta',    initials:'AM', color:'#0ea5e9', class:'X – Arts',         phone:'9932145678', att:72, status:'Pending' },
  { id:'STU2478', name:'Sneha Gupta',    initials:'SG', color:'#10b981', class:'XII – Science B', phone:'9901234567', att:94, status:'Active' },
  { id:'STU2477', name:'Rohan Verma',    initials:'RV', color:'#f59e0b', class:'XI – Science A',  phone:'9812345678', att:81, status:'Active' },
  { id:'STU2476', name:'Anjali Pandey',  initials:'AP', color:'#a855f7', class:'XII – Commerce',  phone:'9745678901', att:65, status:'Suspended' },
  { id:'STU2475', name:'Dev Patel',      initials:'DP', color:'#ec4899', class:'X – Science',     phone:'9867890123', att:91, status:'Active' },
  { id:'STU2474', name:'Kritika Singh',  initials:'KS', color:'#14b8a6', class:'XI – Commerce A', phone:'9823456789', att:78, status:'Active' },
];

function renderStudentsTable() {
  const tbody = document.getElementById('students-tbody');
  if (!tbody) return;
  tbody.innerHTML = studentsData.map(s => {
    const statusClass = s.status === 'Active' ? 'green' : s.status === 'Pending' ? 'yellow' : 'red';
    return `
    <tr>
      <td><input type="checkbox"/></td>
      <td>
        <div class="student-cell">
          <div class="avatar-sm" style="background:${s.color}">${s.initials}</div>
          <div><div class="s-name">${s.name}</div><div class="s-id">#${s.id}</div></div>
        </div>
      </td>
      <td>${s.id}</td>
      <td>${s.class}</td>
      <td>${s.phone}</td>
      <td>
        <div style="display:flex;align-items:center;gap:8px;">
          <div class="att-track" style="width:80px"><div class="att-fill" style="width:${s.att}%;background:${s.att>=85?'#10b981':s.att>=70?'#f59e0b':'#f43f5e'}"></div></div>
          <span style="font-size:12px;color:var(--text-2)">${s.att}%</span>
        </div>
      </td>
      <td><span class="badge-status ${statusClass}">${s.status}</span></td>
      <td>
        <div style="display:flex;gap:4px;">
          <button class="btn-icon" title="View"><i class="fas fa-eye"></i></button>
          <button class="btn-icon" title="Edit"><i class="fas fa-edit"></i></button>
          <button class="btn-icon" title="Delete" style="color:#f43f5e40"><i class="fas fa-trash"></i></button>
        </div>
      </td>
    </tr>`;
  }).join('');
}

// ──────────────────────────────────────────────
// ATTENDANCE TABLE
// ──────────────────────────────────────────────
const attendanceStudents = [
  { name:'Rahul Kumar',   initials:'RK', color:'#6366f1', roll:'01' },
  { name:'Priya Sharma',  initials:'PS', color:'#f43f5e', roll:'02' },
  { name:'Arjun Mehta',   initials:'AM', color:'#0ea5e9', roll:'03' },
  { name:'Sneha Gupta',   initials:'SG', color:'#10b981', roll:'04' },
  { name:'Rohan Verma',   initials:'RV', color:'#f59e0b', roll:'05' },
  { name:'Dev Patel',     initials:'DP', color:'#ec4899', roll:'06' },
  { name:'Anjali Pandey', initials:'AP', color:'#a855f7', roll:'07' },
  { name:'Kritika Singh', initials:'KS', color:'#14b8a6', roll:'08' },
];

let attState = {};
attendanceStudents.forEach((s,i) => attState[i] = 'P');

function setAtt(idx, status, btn) {
  attState[idx] = status;
  const row = btn.closest('tr');
  row.querySelectorAll('.att-radio').forEach(r => r.classList.remove('active-p','active-a','active-l'));
  btn.classList.add(status === 'P' ? 'active-p' : status === 'A' ? 'active-a' : 'active-l');
  updateAttCounts();
}

function updateAttCounts() {
  const vals = Object.values(attState);
  document.getElementById('att-present-count').textContent = vals.filter(v => v === 'P').length;
  document.getElementById('att-absent-count').textContent  = vals.filter(v => v === 'A').length;
  document.getElementById('att-late-count').textContent    = vals.filter(v => v === 'L').length;
}

function renderAttendanceTable() {
  const tbody = document.getElementById('attendance-tbody');
  if (!tbody) return;
  tbody.innerHTML = attendanceStudents.map((s, i) => `
    <tr>
      <td style="color:var(--text-3);font-size:12px">${i+1}</td>
      <td>
        <div class="student-cell">
          <div class="avatar-sm" style="background:${s.color}">${s.initials}</div>
          <div class="s-name">${s.name}</div>
        </div>
      </td>
      <td style="color:var(--text-3)">${s.roll}</td>
      <td>
        <div class="att-radio-group">
          <button class="att-radio ${i<6?'active-p':''}" onclick="setAtt(${i},'P',this)">Present</button>
          <button class="att-radio ${i===6?'active-a':''}" onclick="setAtt(${i},'A',this)">Absent</button>
          <button class="att-radio ${i===7?'active-l':''}" onclick="setAtt(${i},'L',this)">Late</button>
        </div>
      </td>
      <td><input class="form-input" placeholder="Optional remarks..." style="padding:5px 10px;font-size:12px;"/></td>
    </tr>
  `).join('');
  updateAttCounts();
}

// ──────────────────────────────────────────────
// EXAM CARDS
// ──────────────────────────────────────────────
const exams = [
  { subject:'Physics',     title:'Unit Test 3',    class:'XII-A', type:'Unit Test', date:'Mar 18, 2025', time:'10:00 AM', dur:90,  room:'Room 201', marks:40,  emoji:'⚛️',  color:'#6366f1' },
  { subject:'Chemistry',   title:'Mid-Term',       class:'XII-B', type:'Mid-Term',  date:'Mar 20, 2025', time:'09:00 AM', dur:180, room:'Hall A',   marks:100, emoji:'🧪',  color:'#10b981' },
  { subject:'Mathematics', title:'Chapter 7 Test', class:'XI-A',  type:'Unit Test', date:'Mar 22, 2025', time:'11:30 AM', dur:90,  room:'Room 305', marks:40,  emoji:'📐',  color:'#f59e0b' },
  { subject:'English',     title:'Comprehension',  class:'X-C',   type:'Unit Test', date:'Mar 25, 2025', time:'09:00 AM', dur:60,  room:'Room 102', marks:25,  emoji:'📚',  color:'#a855f7' },
  { subject:'Biology',     title:'Practical Exam', class:'XII-A', type:'Practical', date:'Mar 27, 2025', time:'02:00 PM', dur:120, room:'Bio Lab',  marks:30,  emoji:'🔬',  color:'#0ea5e9' },
  { subject:'History',     title:'Mid-Term',       class:'XI-B',  type:'Mid-Term',  date:'Apr 2, 2025',  time:'10:00 AM', dur:180, room:'Hall B',   marks:100, emoji:'🏛️',  color:'#f43f5e' },
];

function renderExamGrid() {
  const grid = document.getElementById('exam-grid');
  if (!grid) return;
  grid.innerHTML = exams.map(ex => `
    <div class="exam-card">
      <div class="exam-card-top">
        <div class="exam-subject-icon" style="background:${ex.color}20;color:${ex.color}">${ex.emoji}</div>
        <span class="badge-status ${ex.type==='Final'?'red':ex.type==='Mid-Term'?'yellow':'blue'}">${ex.type}</span>
      </div>
      <div class="exam-title">${ex.title}</div>
      <div class="exam-sub-title">${ex.subject} &mdash; ${ex.class}</div>
      <div class="exam-meta">
        <div class="exam-meta-row"><i class="fas fa-calendar"></i>${ex.date}</div>
        <div class="exam-meta-row"><i class="fas fa-clock"></i>${ex.time} &nbsp;·&nbsp; ${ex.dur} mins</div>
        <div class="exam-meta-row"><i class="fas fa-map-marker-alt"></i>${ex.room}</div>
        <div class="exam-meta-row"><i class="fas fa-star"></i>Max Marks: ${ex.marks}</div>
      </div>
    </div>
  `).join('');
}

// ──────────────────────────────────────────────
// TIMETABLE
// ──────────────────────────────────────────────
const days = ['Time', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
const periods = [
  { time: '8:00 – 8:45',  slots: [
    { sub:'Physics',   col:'#6366f1' },
    { sub:'English',   col:'#0ea5e9' },
    { sub:'Math',      col:'#f59e0b' },
    { sub:'Chemistry', col:'#10b981' },
    { sub:'Physics',   col:'#6366f1' },
    { sub:'Assembly',  col:'#a855f7' },
  ]},
  { time: '8:45 – 9:30',  slots: [
    { sub:'Chemistry', col:'#10b981' },
    { sub:'Physics',   col:'#6366f1' },
    { sub:'English',   col:'#0ea5e9' },
    { sub:'Math',      col:'#f59e0b' },
    { sub:'Biology',   col:'#ec4899' },
    { sub:'P.E.',      col:'#f43f5e' },
  ]},
  { time: '9:30 – 10:15', slots: [
    { sub:'Math',      col:'#f59e0b' },
    { sub:'Biology',   col:'#ec4899' },
    { sub:'Physics',   col:'#6366f1' },
    { sub:'English',   col:'#0ea5e9' },
    { sub:'Chemistry', col:'#10b981' },
    { sub:'Math',      col:'#f59e0b' },
  ]},
  { time: '10:15 – 10:30',slots: null }, // Break
  { time: '10:30 – 11:15',slots: [
    { sub:'Biology',   col:'#ec4899' },
    { sub:'Math',      col:'#f59e0b' },
    { sub:'Chemistry', col:'#10b981' },
    { sub:'Physics',   col:'#6366f1' },
    { sub:'English',   col:'#0ea5e9' },
    { sub:'Biology',   col:'#ec4899' },
  ]},
  { time: '11:15 – 12:00',slots: [
    { sub:'English',   col:'#0ea5e9' },
    { sub:'Chemistry', col:'#10b981' },
    { sub:'Biology',   col:'#ec4899' },
    { sub:'Chemistry', col:'#10b981' },
    { sub:'Math',      col:'#f59e0b' },
    { sub:'English',   col:'#0ea5e9' },
  ]},
  { time: '12:00 – 12:45',slots: null }, // Lunch
  { time: '12:45 – 1:30', slots: [
    { sub:'Lab',       col:'#14b8a6' },
    { sub:'Lab',       col:'#14b8a6' },
    { sub:'Library',   col:'#a855f7' },
    { sub:'Lab',       col:'#14b8a6' },
    { sub:'Library',   col:'#a855f7' },
    null,
  ]},
  { time: '1:30 – 2:15',  slots: [
    { sub:'Lab',       col:'#14b8a6' },
    { sub:'Lab',       col:'#14b8a6' },
    { sub:'Math',      col:'#f59e0b' },
    { sub:'Lab',       col:'#14b8a6' },
    { sub:'Physics',   col:'#6366f1' },
    null,
  ]},
];

function renderTimetable() {
  const wrap = document.getElementById('timetable-wrap');
  if (!wrap) return;
  let html = '<table class="timetable"><thead><tr>';
  days.forEach(d => html += `<th>${d}</th>`);
  html += '</tr></thead><tbody>';

  periods.forEach(p => {
    if (!p.slots) {
      const label = p.time.includes('10:15') ? '☕ Short Break' : '🍽️ Lunch Break';
      html += `<tr><td style="font-size:11px;color:var(--text-3);text-align:center">${p.time}</td>
               <td colspan="6" style="text-align:center;color:var(--text-3);font-size:12px;background:var(--bg-3)">${label}</td></tr>`;
      return;
    }
    html += `<tr><td style="font-size:11px;color:var(--text-3);text-align:center;white-space:nowrap">${p.time}</td>`;
    p.slots.forEach(slot => {
      if (!slot) {
        html += `<td><span class="tt-free">—</span></td>`;
      } else {
        html += `<td><div class="tt-slot" style="background:${slot.col}18;color:${slot.col};border-left:2px solid ${slot.col}">${slot.sub}</div></td>`;
      }
    });
    html += '</tr>';
  });

  html += '</tbody></table>';
  wrap.innerHTML = html;
}

// ──────────────────────────────────────────────
// CAFETERIA MENUS
// ──────────────────────────────────────────────
const menus = {
  breakfast: [
    { emoji:'🥣', name:'Poha with Chutney',   cal:'320 kcal', price:'₹30' },
    { emoji:'🧇', name:'Bread & Omelette',     cal:'410 kcal', price:'₹40' },
    { emoji:'🥛', name:'Milk (250ml)',          cal:'155 kcal', price:'₹15' },
    { emoji:'🍌', name:'Seasonal Fruits',       cal:'90 kcal',  price:'₹25' },
  ],
  lunch: [
    { emoji:'🍛', name:'Dal Makhani + Rice',   cal:'560 kcal', price:'₹70' },
    { emoji:'🫓', name:'Chapati (4 pcs)',       cal:'220 kcal', price:'₹20' },
    { emoji:'🥗', name:'Mixed Salad',           cal:'80 kcal',  price:'₹25' },
    { emoji:'🍮', name:'Curd (100g)',           cal:'98 kcal',  price:'₹15' },
    { emoji:'🍹', name:'Buttermilk',            cal:'40 kcal',  price:'₹10' },
  ],
  snacks: [
    { emoji:'🍵', name:'Masala Chai',           cal:'90 kcal',  price:'₹10' },
    { emoji:'🥐', name:'Samosa (2 pcs)',         cal:'320 kcal', price:'₹20' },
    { emoji:'🍪', name:'Biscuits',              cal:'180 kcal', price:'₹15' },
    { emoji:'🌮', name:'Veg Frankie',            cal:'380 kcal', price:'₹40' },
  ]
};

function switchMenu(type, btn) {
  document.querySelectorAll('.menu-tab').forEach(t => t.classList.remove('active'));
  btn.classList.add('active');
  renderMenuItems(type);
}

function renderMenuItems(type = 'breakfast') {
  const container = document.getElementById('menu-items');
  if (!container) return;
  container.innerHTML = menus[type].map(item => `
    <div class="menu-item">
      <div class="menu-item-left">
        <span class="menu-emoji">${item.emoji}</span>
        <div>
          <div class="menu-item-name">${item.name}</div>
          <div class="menu-item-cal">${item.cal}</div>
        </div>
      </div>
      <div class="menu-item-price">${item.price}</div>
    </div>
  `).join('');
}

// ──────────────────────────────────────────────
// NOTIFICATIONS
// ──────────────────────────────────────────────
const notifications = [
  { icon:'fas fa-clipboard-check', color:'#6366f1', title:'Attendance Alert',       desc:'Attendance in XII-B dropped to 82% this week', time:'2 min ago', unread:true },
  { icon:'fas fa-rupee-sign',      color:'#f59e0b', title:'Fee Due Reminder',        desc:'47 students have unpaid fees this month',       time:'1 hr ago',  unread:true },
  { icon:'fas fa-file-alt',        color:'#10b981', title:'Exam Scheduled',          desc:'Chemistry Mid-Term scheduled for Mar 20',       time:'3 hr ago',  unread:true },
  { icon:'fas fa-user-plus',       color:'#a855f7', title:'New Student Enrolled',    desc:'Kritika Singh joined XI Commerce A',             time:'Yesterday', unread:true },
  { icon:'fas fa-book',            color:'#0ea5e9', title:'Library Overdue Books',   desc:'87 books are overdue from 43 students',          time:'2 days ago', unread:false },
  { icon:'fas fa-utensils',        color:'#ec4899', title:'Cafeteria Low Balance',   desc:'3 student meal cards have zero balance',         time:'2 days ago', unread:false },
];

function renderNotifications() {
  const list = document.getElementById('notif-list');
  if (!list) return;
  list.innerHTML = notifications.map(n => `
    <div class="notif-item">
      <div class="notif-icon" style="background:${n.color}20;color:${n.color}">
        <i class="${n.icon}"></i>
      </div>
      <div class="notif-body">
        <div class="notif-title">${n.title}</div>
        <div class="notif-desc">${n.desc}</div>
      </div>
      <div style="display:flex;flex-direction:column;align-items:flex-end;gap:6px">
        <div class="notif-time">${n.time}</div>
        ${n.unread ? '<div class="notif-dot"></div>' : ''}
      </div>
    </div>
  `).join('');
}

// ──────────────────────────────────────────────
// SETTINGS PANEL
// ──────────────────────────────────────────────
function switchSettings(section, btn) {
  document.querySelectorAll('.settings-tab').forEach(t => t.classList.remove('active'));
  document.querySelectorAll('.settings-section').forEach(s => s.classList.add('hidden'));
  btn.classList.add('active');
  const target = document.getElementById('s-' + section);
  if (target) target.classList.remove('hidden');
}

// ──────────────────────────────────────────────
// TODAY DATE
// ──────────────────────────────────────────────
function setTodayDate() {
  const el = document.getElementById('today-date');
  if (!el) return;
  const now = new Date();
  el.textContent = now.toLocaleDateString('en-IN', { weekday:'short', day:'numeric', month:'short' });
}

function setAttDate() {
  const el = document.getElementById('att-date');
  if (!el) return;
  const now = new Date();
  el.value = now.toISOString().split('T')[0];
}

// ──────────────────────────────────────────────
// LIVE CLOCK in topbar (subtle)
// ──────────────────────────────────────────────
function startClock() {
  const el = document.getElementById('breadcrumb');
  // Don't override breadcrumb; skip or use another element
}

// ──────────────────────────────────────────────
// INIT
// ──────────────────────────────────────────────
document.addEventListener('DOMContentLoaded', () => {
  renderStudentsTable();
  renderAttendanceTable();
  renderExamGrid();
  renderTimetable();
  renderMenuItems('breakfast');
  renderNotifications();
  setTodayDate();
  setAttDate();
  navigateTo('dashboard');

  // Save attendance button feedback
  document.querySelectorAll('.btn-primary').forEach(btn => {
    if (btn.textContent.trim().includes('Save Attendance')) {
      btn.addEventListener('click', () => showToast('✅ Attendance saved successfully!'));
    }
    if (btn.textContent.trim().includes('Add Student')) {
      // handled by modal
    }
    if (btn.textContent.trim().includes('Save Changes')) {
      btn.addEventListener('click', () => showToast('✅ Settings saved!'));
    }
    if (btn.textContent.trim().includes('Publish Results')) {
      btn.addEventListener('click', () => showToast('📢 Results published to students!'));
    }
  });

  // Student search filter
  const searchInput = document.querySelector('.table-search');
  if (searchInput) {
    searchInput.addEventListener('input', () => {
      const q = searchInput.value.toLowerCase();
      document.querySelectorAll('#students-tbody tr').forEach(row => {
        row.style.display = row.textContent.toLowerCase().includes(q) ? '' : 'none';
      });
    });
  }
});

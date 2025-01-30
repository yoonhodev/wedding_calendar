document.addEventListener("DOMContentLoaded" ,async function () {

    console.log('wedding.js start');
    await fetchCustomerList();
})

const openModal = document.getElementById('openModal');
const closeModal = document.querySelectorAll('.close');
const writeModal = document.getElementById('writeModal');

const testModal = document.getElementById('testModal');
const updateModal = document.getElementById('updateModal');

// 모달 열기
openModal.addEventListener('click', () => {
    writeModal.showModal();
});

testModal.addEventListener('click', () => {
    updateModal.showModal();
})

// 닫기
closeModal.forEach((button) => {
    button.addEventListener('click', () => {
        document.getElementById('writeModal').close();
        document.getElementById('updateModal').close();
    });
});

document.addEventListener('DOMContentLoaded', () => {
    const addOptionBtn = document.getElementById('addOptionBtn');
    const optionSelectWrapper = document.getElementById('optionSelectWrapper');
    const optionSelect = document.getElementById('optionSelect');
    const dateLabel = document.getElementById('dateLabel');
    const dateInput = document.getElementById('dateInput');

    // + 버튼 클릭 시 분류 선택 보여주기
    addOptionBtn.addEventListener('click', () => {
        optionSelectWrapper.classList.toggle('hidden');
    });

    // 분류 선택 시 "신청 날짜"에 옵션 반영
    optionSelect.addEventListener('change', (e) => {
        const selectedOption = e.target.value;
        dateLabel.textContent = `분류: ${selectedOption}`;
        dateInput.type = 'date'; // 달력 표시
    });
});

document.addEventListener('DOMContentLoaded', () => {
    const dynamicFields = document.getElementById('dynamicFields');
    const addRowBtn = document.getElementById('addRowBtn');
    const maxRows = 5;

    // Add a new row
    addRowBtn.addEventListener('click', () => {
        const currentRows = dynamicFields.querySelectorAll('.dynamic-row').length;
        if (currentRows >= maxRows) {
            alert('최대 5개의 항목만 추가할 수 있습니다.');
            return;
        }

        const newRow = document.createElement('div');
        newRow.classList.add('dynamic-row', 'flex', 'items-center', 'gap-4');

        // 항목 선택
        const selectBox = document.createElement('select');
        selectBox.classList.add(
            'w-1/2',
            'bg-gray-100',
            'rounded-lg',
            'px-3',
            'py-2',
            'text-sm',
            'focus:outline-none',
            'focus:ring',
            'focus:ring-blue-200',
            'text-black-100'
        );
        selectBox.innerHTML = `
                <option value="" disabled selected>항목 선택</option>
                <option value="dressTour">드레스투어</option>
                <option value="suitRehearsal">촬영 가봉</option>
                <option value="takeRehearsal">리허설 촬영</option>
                <option value="suitWedding">본식 가봉</option>
                <option value="wedding">본식</option>
            `;

        // 날짜 선택
        const dateInput = document.createElement('input');
        dateInput.type = 'date';
        dateInput.disabled = true; // 초기 상태에서 비활성화
        dateInput.classList.add(
            'w-1/2',
            'bg-gray-100',
            'rounded-lg',
            'px-3',
            'py-2',
            'text-sm',
            'focus:outline-none',
            'focus:ring',
            'focus:ring-blue-200'
        );

        // - 버튼
        const removeBtn = document.createElement('button');
        removeBtn.classList.add(
            'bg-gray-300',
            'text-gray-700',
            'px-3',
            'py-1',
            'rounded-lg',
            'hover:bg-gray-400'
        );
        removeBtn.textContent = '-';

        // Remove row functionality
        removeBtn.addEventListener('click', () => {
            newRow.remove();
        });

        // Enable date input on select
        selectBox.addEventListener('change', () => {
            if (selectBox.value) {
                dateInput.disabled = false;
            } else {
                dateInput.disabled = true;
            }
        });

        // Append elements to row
        newRow.appendChild(selectBox);
        newRow.appendChild(dateInput);
        newRow.appendChild(removeBtn);

        // Append row to container
        dynamicFields.appendChild(newRow);
    });
});

document.addEventListener('DOMContentLoaded', () => {
    const editBtn = document.getElementById('editBtn');
    const checkboxes = document.querySelectorAll('.form-checkbox');
    const selectBox = document.querySelector('select');
    const inputFields = document.querySelectorAll('input[type="text"]');

    // 초기 버튼 상태
    let isEditing = false;

    // Toggle edit mode
    editBtn.addEventListener('click', () => {
        if (!isEditing) {
            // Enable fields for editing
            checkboxes.forEach((checkbox) => (checkbox.disabled = false));
            selectBox.disabled = false;
            inputFields.forEach((input) => (input.disabled = false));
            editBtn.textContent = '저장';
            editBtn.classList.replace('bg-blue-500', 'bg-green-500');
            editBtn.classList.replace('hover:bg-blue-600', 'hover:bg-green-600');
            isEditing = true;
        } else {
            // Save changes and disable fields
            checkboxes.forEach((checkbox) => (checkbox.disabled = true));
            selectBox.disabled = true;
            inputFields.forEach((input) => (input.disabled = true));
            editBtn.textContent = '수정';
            editBtn.classList.replace('bg-green-500', 'bg-blue-500');
            editBtn.classList.replace('hover:bg-green-600', 'hover:bg-blue-600');
            isEditing = false;

            // Optionally: Save data here or handle form submission
            alert('수정 내용이 저장되었습니다.');
        }
    });
});

const fetchCustomerList = async () => {
    try {
        const response = await fetch('/customer');
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();

        createCalendar(data.data);
        createGuideList(data.data);

    } catch (error) {
        console.error('Error fetching customer data:', error);
    }
};

const createCalendar = (data) => {

    console.log('data :', data);
    const calendarList = document.getElementById('calendarList');
    if (!calendarList) {
        console.error("Element with id 'calendarList' not found.");
        return;
    }

    let html = '';
    for (let customer of data) {
        html += `
        <tr class="border-b border-slate-200">
            <td class="text-center whitespace-nowrap">${customer.husbandName} / ${customer.wifeName}</td>
            ${createTdColumns(customer)}
        </tr>
        `;
    }
    calendarList.innerHTML = html;
};

const createTdColumns = (customer) => {

    const events = customer.events
    console.log('customer :', customer);
    const eventTypes = ['드레스 투어', '촬영 가봉', '리허설 촬영', '본식 가봉', '본식'];
    let tdHtml = '';

    for (let type of eventTypes) {
        const targetEvent = events.find(event => event.eventType === type);
        const orderStatus = targetEvent ? targetEvent.orderStatus || '상태 없음' : '미등록';
        const newOrderStatus = transformOrderStatus(orderStatus);
        const deactivateClass = newOrderStatus.includes('대기') ? 'text-gray-400' : 'text-blue-400';
        const dDay = targetEvent ? targetEvent.dDay || 'N/A' : 'N/A';

        // D-Day 계산
        const dDayText = targetEvent ? calculateDDay(dDay) : 'N/A';

        // D+ 여부 확인 (디데이 초과 시)
        const isPast = dDayText.startsWith('D+');

        // TD 클래스에 조건부 스타일 추가
        tdHtml += `
            <td class="p-4 text-center text-sm relative ${isPast ? 'bg-gray-100 text-gray-300' : ''}" onclick="openUpdatePopup('${encodeURIComponent(JSON.stringify(customer))}','${type}')">
                ${targetEvent ? `${dDayText}` : 'N/A'}
                <div class="absolute bottom-3 left-1 text-[10px] ${deactivateClass}">
                    ${newOrderStatus}
                </div>
                <div class="absolute bottom-1 left-1 flex space-x-1">
                    <span class="w-2 h-2 bg-orange-300 rounded-full"></span>
                    <span class="w-2 h-2 bg-yellow-200 rounded-full"></span>
                    <span class="w-2 h-2 bg-green-300 rounded-full"></span>
                </div>
                <div class="absolute bottom-1 right-1 text-xs text-gray-400">${formatDate(dDay)}</div>
            </td>
        `;
    }

    // 추가 열 생성
    tdHtml += `
        <td class="p-4 text-center text-sm relative">
            test
        </td>
        <td class="p-4 text-center text-sm relative">
            test
        </td>
    `;

    return tdHtml;
};

const transformOrderStatus = (status) => {

    switch (status) {

        case 'orderWait':
            return '발주 대기';
        case 'orderComplete':
            return '발주 완료';
        case 'secondOrderWait':
            return '2차 발주 대기';
        case 'secondOrderComplete':
            return '2차 발주 완료';
        default:
            return '';
    }
}

const calculateDDay = (date) => {
    const targetDate = new Date(date);
    const today = new Date();

    // 시간 부분 초기화
    today.setHours(0, 0, 0, 0);
    targetDate.setHours(0, 0, 0, 0);

    const diffTime = targetDate - today;
    const dDay = Math.ceil(diffTime / (1000 * 60 * 60 * 24)); // 날짜 차이 계산

    // D-Day 형식 반환
    if (dDay > 0) {
        return `D-${dDay}`; // 남은 날 (양수)
    } else if (dDay === 0) {
        return `D-Day`; // 오늘
    } else {
        return `D+${Math.abs(dDay)}`; // 지난 날 (음수)
    }
};

const formatDate = (date) => {
    if (!date || date === 'N/A') return 'N/A';
    const d = new Date(date);
    return `${d.getFullYear()}.${d.getMonth() + 1}.${d.getDate()}`;
};

const createGuideList = (data) => {
    const dDayList = document.getElementById('dDayList');
    const d31 = document.getElementById(('D-31'));
    const d14 = document.getElementById('D-14');
    const d2 = document.getElementById('D-2');

    // D-Day 항목 초기화
    const dDayStructure = {
        'D-31': [],
        'D-14': [],
        'D-2': []
    };

    // 데이터 매핑
    data.forEach(item => {
        item.events.forEach(event => {
            const dDay = calculateDDay(event.dDay); // D-Day 계산
            console.log('dDay :', dDay);

            if (dDayStructure[dDay] !== undefined) {
                dDayStructure[dDay].push(`${item.husbandName} / ${item.wifeName} - ${event.eventType}`);
            }
        });
    });

    // D-Day 리스트 생성
    let dDayHtml = `
        <ul class="space-y-4">
            <li class="flex items-center justify-between px-2 sm:px-3 py-1 rounded-md bg-[#f3f7fc] border-r-4 border-[#b6cee1] text-[#476c8a]">
                D-31
                <span class="w-2 h-2 bg-orange-300 rounded-full"></span>
            </li>
            ${dDayStructure['D-31'].length > 0
        ? dDayStructure['D-31'].map(name => `<li class="text-gray-500 text-left ml-4">${name}</li>`).join('')
        : ''}

            <li class="flex items-center justify-between px-2 sm:px-3 py-1 rounded-md bg-[#f3f7fc] border-r-4 border-[#b6cee1] text-[#476c8a]">
                D-14
                <span class="w-2 h-2 bg-yellow-300 rounded-full"></span>
            </li>
            ${dDayStructure['D-14'].length > 0
        ? dDayStructure['D-14'].map(name => `<li class="text-gray-500 text-left ml-4">${name}</li>`).join('')
        : ''}

            <li class="flex items-center justify-between px-2 sm:px-3 py-1 rounded-md bg-[#f3f7fc] border-r-4 border-[#b6cee1] text-[#476c8a]">
                D-2
                <span class="w-2 h-2 bg-green-300 rounded-full"></span>
            </li>
            ${dDayStructure['D-2'].length > 0
        ? dDayStructure['D-2'].map(name => `<li class="text-gray-500 text-left ml-4">${name}</li>`).join('')
        : ''}
        </ul>
    `;

    // 리스트 삽입
    dDayList.innerHTML = dDayHtml;


    // count 삽입
    d31.innerHTML = dDayStructure['D-31'].length;
    d14.innerHTML = dDayStructure['D-14'].length;
    d2.innerHTML = dDayStructure['D-2'].length;
};

const openUpdatePopup = (encodedCustomer, eventType) => {
    const updateModalTitle = document.querySelector('#updateModal h3');
    const orderStatus = document.getElementById('orderStatus');
    const customer = JSON.parse(decodeURIComponent(encodedCustomer));
    console.log('Customer Data:', customer);

    // 선택한 이벤트 타입에 따라 이벤트 찾기
    const targetEvent = customer.events.find(event => event.eventType === eventType);

    console.log('targetEvent :', targetEvent);

    // 제목 업데이트
    updateModalTitle.textContent = `${customer.husbandName} / ${customer.wifeName} ${eventType}`;

    // 체크박스 요소 가져오기
    const d31Checkbox = document.getElementById('checkboxD31');
    const d14Checkbox = document.getElementById('checkboxD14');
    const d2Checkbox = document.getElementById('checkboxD2');

    // 해당 이벤트가 있으면 값 설정, 없으면 false
    d31Checkbox.checked = targetEvent ? targetEvent.guide31Days === 1 : false;
    d14Checkbox.checked = targetEvent ? targetEvent.guide14Days === 1 : false;
    d2Checkbox.checked = targetEvent ? targetEvent.guide2Days === 1 : false;

    // 발주 상태 select box 설정
    const orderStatusSelect = document.getElementById('orderStatus');
    if (targetEvent) {
        orderStatusSelect.value = targetEvent.orderStatus || '';
    } else {
        orderStatusSelect.value = ''; // 기본값 설정
    }

    // 팝업 열기
    const modal = document.getElementById('updateModal');
    modal.showModal();
};

const saveCustomerInfo = async () => {

    try {
        const response = await fetch('/customer')
    } catch (error) {
        console.error(error);
    }
}

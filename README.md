PaymentApp

Android-приложение для управления платежами и историей транзакций с функцией экспорта PDF.

📌 Особенности
	•	Калькулятор с поддержкой операций +, -, ×, ÷ и точностью до двух знаков после запятой.
	•	Создание и управление платежами через ViewModel и PaymentUseCase.
	•	История транзакций с сохранением данных.
	•	Экспорт истории транзакций в PDF с возможностью сохранения на устройстве.
	•	Интеграция с Jetpack Compose и/или стандартным Android UI.
	•	Полностью покрыт unit-тестами (CalculatorEngine, MainViewModel, HistoryViewModel, TransactionRepository).

PaymentApp/
│
├─ app/
│  ├─ src/main/java/uz/example/paymentapp/
│  │  ├─ domain/
│  │  │   ├─ model/           # Модели данных (PaymentInfo, Transaction)
│  │  │   ├─ repository/      # Интерфейсы репозиториев
│  │  │   └─ usecase/         # UseCases (PaymentUseCase)
│  │  ├─ data/
│  │  │   └─ local/           # Реализация репозиториев
│  │  ├─ presentation/
│  │  │   ├─ ui/               # Фрагменты и Activity
│  │  │   └─ viewmodel/        # ViewModel классы
│  │  └─ utils/                # Вспомогательные классы (PdfExportUtils)
│  └─ res/                     # Ресурсы (layouts, drawables, strings)
│
├─ build.gradle
└─ settings.gradle


🧩 Архитектура

Проект использует MVVM с разделением на слои:
	•	Domain — бизнес-логика и модели.
	•	Data — реализация репозиториев (локальное хранилище).
	•	Presentation — ViewModel и UI-фрагменты.
	•	Utils — вспомогательные классы (PDF-экспорт).

LiveData используется для реактивного обновления UI.
ViewModel обеспечивает управление состоянием без утечек памяти.

⸻

🛠 Используемые библиотеки
	•	Jetpack ViewModel & LiveData
	•	Room (для локального хранения данных, если нужно расширить функционал)
	•	iText — генерация PDF
	•	Mockito & JUnit — unit-тестирование

